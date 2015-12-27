package org.robbins.load.tester.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.load.tester.message.BatchTestResult;
import org.robbins.load.tester.message.BatchTestStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class BatchLoadTester extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchLoadTester.class);

    private final GenericRestCrudFacade client;

    public BatchLoadTester(final GenericRestCrudFacade client) {
        LOGGER.debug("Creating BatchLoadTester");

        this.client = client;
    }

    public static Props props(final GenericRestCrudFacade client) {
        return Props.create(BatchLoadTester.class, () -> new BatchLoadTester(client));
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

        final BatchLoadingReceiptDto receipt = client.save(testStartMessage.getBatch());
        final BatchTestResult testResult = new BatchTestResult(testStartMessage.getEndPointName(), receipt);

        LOGGER.debug("Sending BatchTestResult message: {}", testResult.toString());
        sender.tell(testResult, self());
    }
}
