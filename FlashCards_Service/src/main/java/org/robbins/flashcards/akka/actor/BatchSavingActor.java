package org.robbins.flashcards.akka.actor;

import java.util.List;

import org.robbins.flashcards.akka.message.Messages;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class BatchSavingActor extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchSavingActor.class);

    private GenericCrudFacade facade;
    private Long batchId;
    private Integer successCount = 0;
    private Integer failureCount = 0;

    public BatchSavingActor() {
        LOGGER.debug("Creating BatchSavingActor");
    }

    public static Props props() {
        return Props.create(BatchSavingActor.class, BatchSavingActor::new);
    }

    @Override
    public void preStart() throws Exception
    {
        super.preStart();
        context().parent().tell(new Messages.GiveMeWork(), self());
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(Messages.SingleBatchSaveStartMessage.class, startSave -> doSave(startSave, sender()))
                .matchAny(o -> LOGGER.error("Received Unknown message"))
                .build();
    }

    private void doSave(final Messages.SingleBatchSaveStartMessage startSaveMessage, final ActorRef sender) {
        LOGGER.trace("Received SingleBatchSaveStartMessage message: {}", startSaveMessage.toString());

        this.facade = startSaveMessage.facade();
        this.batchId = startSaveMessage.batchId();

        final Messages.SingleBatchSaveResultMessage result = saveBatch(startSaveMessage.dtos());

        LOGGER.trace("Sending SingleBatchSaveResultMessage message: {}", result.toString());
        sender.tell(result, self());
    }

    private Messages.SingleBatchSaveResultMessage saveBatch(final List<AbstractAuditableDto> batch) {
        final BatchLoadingReceiptDto receipt = facade.save(batch);
        successCount = receipt.getSuccessCount();
        failureCount = getFailureCount(batch.size());

        final Messages.SingleBatchSaveResultMessage result = new Messages.SingleBatchSaveResultMessage(successCount, failureCount, batchId);
        resetCounters();
        return result;
    }

    private int getFailureCount(final int totalCount) {
        return totalCount - successCount;
    }

    private void resetCounters() {
        this.successCount = 0;
        this.failureCount = 0;
    }
}
