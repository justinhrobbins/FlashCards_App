package org.robbins.load.tester.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.FromConfig;
import org.robbins.flashcards.client.TagClient;
import org.robbins.load.tester.message.LoadTestResult;
import org.robbins.load.tester.message.TestStart;
import org.robbins.load.tester.message.TestResult;
import org.robbins.load.tester.message.LoadTestStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.stream.LongStream;

@Named("loadTestingCoordinator")
@Scope("prototype")
public class LoadTestingCoordinator extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadTestingCoordinator.class);

    @Inject
    private TagClient tagClient;

    private ActorRef parent;
    private Long totalTestsToInvoke;
    private Long completedTestCount = 0L;
    private Long totalDuration = 0L;
    private Long successCount = 0L;
    private Long failureCount = 0L;

    public LoadTestingCoordinator() {
        LOGGER.debug("Creating LoadTestingCoordinator");
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(LoadTestStart.class, startLoadTest -> loadTestStart(startLoadTest))
                .match(TestResult.class, loadTestResult -> loadTestFinish(loadTestResult))
                .matchAny(o -> LOGGER.info("Received Unknown message"))
                .build();
    }

    private void loadTestStart(final LoadTestStart loadTestStartMessage) {
        LOGGER.info("Received LoadTestStart message: {}", loadTestStartMessage.toString());

        parent = sender();
        totalTestsToInvoke = loadTestStartMessage.getEndPointInvocationCount();

        ActorRef loadTestingActor = context().actorOf(FromConfig.getInstance().props(LoadTester.props(tagClient)), "load-tester");

        LongStream.range(1, loadTestStartMessage.getEndPointInvocationCount() + 1).forEach(i ->
                        loadTestingActor.tell(new TestStart(loadTestStartMessage.getEndPointName(), i), self())
        );
    }

    private void loadTestFinish(TestResult testResult) {
        LOGGER.debug("Received TestResult message: {}", testResult.toString());

        totalDuration += testResult.getDuration();
        completedTestCount++;
        if (testResult.getResultStatus().equals(TestResult.TestResultStatus.SUCCESS)) {
            successCount++;
        } else {
            failureCount++;
        }

        if (completedTestCount.equals(totalTestsToInvoke)) {
            LoadTestResult loadTestResult = new LoadTestResult(this.completedTestCount, testResult.getEndPointName(),
                    this.successCount, this.failureCount, this.totalDuration);
            LOGGER.info("LoadTestResult: {}", loadTestResult);
            parent.tell(loadTestResult, self());
        }

    }
}
