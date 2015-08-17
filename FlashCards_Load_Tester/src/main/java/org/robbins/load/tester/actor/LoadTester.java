package org.robbins.load.tester.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.robbins.flashcards.client.TagClient;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.builder.TagDtoBuilder;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.load.tester.message.TestStart;
import org.robbins.load.tester.message.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.UUID;

public class LoadTester extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadTester.class);

    private final TagClient tagClient;

    public LoadTester(final TagClient tagClient) {
        LOGGER.debug("Creating LoadTestingActor");

        this.tagClient = tagClient;
    }

    public static Props props(final TagClient tagClient) {
        return Props.create(LoadTester.class, () -> new LoadTester(tagClient));
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

        final TagDto tag = new TagDtoBuilder().withName("load-tester-" + UUID.randomUUID().toString() + "-" + testStartMessage.getTestId()).build();
        TestResult result = saveItem(testStartMessage, tag);

        LOGGER.debug("Sending TestResult message: {}", result.toString());
        sender.tell(result, self());
    }

    private TestResult saveItem(final TestStart testStartMessage, final TagDto tag) {
        long duration;
        TestResult.TestResultStatus resultStatus = TestResult.TestResultStatus.SUCCESS;

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            tagClient.save(tag);

        } catch (FlashcardsException e) {
            LOGGER.error("Unable to create Tag {}, error: {}", tag.toString(), e.getMessage());
            resultStatus = TestResult.TestResultStatus.FAILURE;
        }

        stopWatch.stop();
        duration = stopWatch.getLastTaskTimeMillis();

        return new TestResult(testStartMessage.getEndPointName(), duration, resultStatus);
    }
}
