package org.robbins.load.tester.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.FromConfig;
import com.google.common.collect.Lists;
import org.robbins.flashcards.client.TagClient;
import org.robbins.flashcards.dto.BulkLoadingReceiptDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.load.tester.message.*;
import org.robbins.load.tester.util.LoadingTestingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Named("loadTestingCoordinator")
@Scope("prototype")
public class LoadTestingCoordinator extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadTestingCoordinator.class);

    @Inject
    private TagClient tagClient;

    private ActorRef parent;
    private Integer totalTestsToInvoke;
    private Integer completedTestCount = 0;
    private Long totalDuration = 0L;
    private Integer successCount = 0;
    private Integer failureCount = 0;

    public LoadTestingCoordinator() {
        LOGGER.debug("Creating LoadTestingCoordinator");
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(LoadTestStart.class, this::loadTestStart)
                .match(SingleTestResult.class, this::individualLoadTestFinish)
                .match(BatchTestResult.class, this::batchLoadTestFinish)
                .matchAny(o -> LOGGER.info("Received Unknown message"))
                .build();
    }

    private void loadTestStart(final LoadTestStart loadTestStartMessage) {
        LOGGER.debug("Received LoadTestStart message: {}", loadTestStartMessage.toString());

        parent = sender();
        totalTestsToInvoke = loadTestStartMessage.getTotalLoadCount();

        if (loadTestStartMessage.getBatchSize().equals(1)) {
            saveItemsIndividually(loadTestStartMessage);
        }
        else {
            saveItemsInBatches(loadTestStartMessage);
        }
    }

    private void saveItemsInBatches(final LoadTestStart loadTestStartMessage) {
        LOGGER.debug("Starting batch load test");

        final List<List<TagDto>> batches = LoadingTestingUtil.createTagDtosInBatches(
                loadTestStartMessage.getTotalLoadCount(), loadTestStartMessage.getBatchSize());

        ActorRef batchLoadTestingActor = context().actorOf(FromConfig.getInstance().props(BatchLoadTester.props(tagClient)), "batch-load-tester");

        batches.forEach(batch -> batchLoadTestingActor.tell(new BatchTestStart(loadTestStartMessage.getEndPointName(), batch), self()));
    }

    private void saveItemsIndividually(final LoadTestStart loadTestStartMessage) {
        LOGGER.debug("Starting load test");

        ActorRef loadTestingActor = context().actorOf(FromConfig.getInstance().props(LoadTester.props(tagClient)), "load-tester");

        LongStream.range(1, loadTestStartMessage.getTotalLoadCount() + 1)
                .forEach(i ->
                                loadTestingActor.tell(new TestStart(loadTestStartMessage.getEndPointName(), i), self())
                );
    }

    private void individualLoadTestFinish(SingleTestResult testResult) {
        LOGGER.debug("Received SingleTestResult message: {}", testResult.toString());

        totalDuration += testResult.getDuration();
        completedTestCount++;
        if (testResult.getResultStatus().equals(SingleTestResult.TestResultStatus.SUCCESS)) {
            successCount++;
        } else {
            failureCount++;
        }

        if (completedTestCount.equals(totalTestsToInvoke)) {
            completeLoadTest(testResult.getEndPointName());
        }
    }

    private void batchLoadTestFinish(final BatchTestResult testResult) {
        LOGGER.debug("Received BatchTestResult message: {}", testResult.toString());

        final BulkLoadingReceiptDto receipt = testResult.getReceipt();

        totalDuration += LoadingTestingUtil.calculateLoadingDuration(receipt.getStartTime(), receipt.getEndTime());
        completedTestCount += (receipt.getSuccessCount() + receipt.getFailureCount());
        successCount += receipt.getSuccessCount();
        failureCount += receipt.getFailureCount();
        LOGGER.debug("completedTestCount: {}, totalTestsToInvoke: {}", completedTestCount, totalTestsToInvoke);
        if (completedTestCount.equals(totalTestsToInvoke)) {
            completeLoadTest(testResult.getEndPointName());
        }
    }

    private void completeLoadTest(final String endPointName) {
        LoadTestResult loadTestResult = new LoadTestResult(this.completedTestCount, endPointName,
                this.successCount, this.failureCount, this.totalDuration);
        LOGGER.debug("LoadTestResult: {}", loadTestResult);
        parent.tell(loadTestResult, self());
        context().stop(self());
    }
}
