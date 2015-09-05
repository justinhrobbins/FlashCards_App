package org.robbins.load.tester.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.robbins.flashcards.client.TagClient;
import org.robbins.flashcards.dto.BulkLoadingReceiptDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.load.tester.message.BatchTestResult;
import org.robbins.load.tester.message.BatchTestStart;
import org.robbins.load.tester.message.SingleTestResult;
import org.robbins.load.tester.message.TestStart;
import org.robbins.load.tester.util.LoadingTestingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.UUID;

public class BatchLoadTester extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchLoadTester.class);

    private final TagClient tagClient;

    public BatchLoadTester(final TagClient tagClient) {
        LOGGER.debug("Creating BatchLoadTester");

        this.tagClient = tagClient;
    }

    public static Props props(final TagClient tagClient) {
        return Props.create(BatchLoadTester.class, () -> new BatchLoadTester(tagClient));
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(BatchTestStart.class, startLoadTest -> doLoadTest(startLoadTest, sender()))
                .matchAny(o -> LOGGER.error("Received Unknown message"))
                .build();
    }

    private void doLoadTest(final BatchTestStart testStartMessage, final ActorRef sender) {
        LOGGER.debug("Received BatchTestStart message: {}", testStartMessage.toString());

        final BulkLoadingReceiptDto receipt = tagClient.save(testStartMessage.getBatch());
        final BatchTestResult testResult = new BatchTestResult(testStartMessage.getEndPointName(), receipt);

        LOGGER.debug("Sending BatchTestResult message: {}", testResult.toString());
        sender.tell(testResult, self());
    }
}
