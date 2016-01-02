package org.robbins.load.tester.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.load.tester.message.SingleTestResult;
import org.robbins.load.tester.message.TestStart;
import org.robbins.load.tester.util.LoadingTestingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.UUID;

public class LoadTester extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadTester.class);

    private final GenericRestCrudFacade client;

    public LoadTester(final GenericRestCrudFacade client) {
        LOGGER.debug("Creating LoadTestingActor");

        this.client = client;
    }

    public static Props props(final GenericRestCrudFacade client) {
        return Props.create(LoadTester.class, () -> new LoadTester(client));
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(TestStart.class, startLoadTest -> doLoadTest(startLoadTest, sender()))
                .matchAny(o -> LOGGER.error("Received Unknown message"))
                .build();
    }

    private void doLoadTest(final TestStart testStartMessage, final ActorRef sender) {
        LOGGER.debug("Received TestStart message: {}", testStartMessage.toString());

        final AbstractAuditableDto dto = LoadingTestingUtil.createDto("load-tester-" + UUID.randomUUID().toString() + "-" + testStartMessage.getTestId(), testStartMessage.getDtoClass());
        SingleTestResult result = saveItem(testStartMessage, dto);

        LOGGER.debug("Sending TestResult message: {}", result.toString());
        sender.tell(result, self());
    }

    private <D extends AbstractAuditableDto> SingleTestResult saveItem(final TestStart testStartMessage, final D dto) {
        long duration;
        SingleTestResult.TestResultStatus resultStatus = SingleTestResult.TestResultStatus.SUCCESS;

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            client.save(dto);

        } catch (FlashCardsException e) {
            LOGGER.error("Unable to create Dto {}, error: {}", dto.toString(), e.getMessage());
            resultStatus = SingleTestResult.TestResultStatus.FAILURE;
        }

        stopWatch.stop();
        duration = stopWatch.getLastTaskTimeMillis();

        return new SingleTestResult(testStartMessage.getEndPointName(), duration, resultStatus);
    }
}
