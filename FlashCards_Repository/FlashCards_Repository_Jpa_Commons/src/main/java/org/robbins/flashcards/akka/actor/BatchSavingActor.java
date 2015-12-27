package org.robbins.flashcards.akka.actor;

import java.util.List;
import java.util.stream.Collectors;

import org.robbins.flashcards.akka.message.SingleBatchSaveResultMessage;
import org.robbins.flashcards.akka.message.SingleBatchSaveStartMessage;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.model.util.AuditingUtil;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
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

    private FlashCardsAppRepository repository;
    private DtoConverter converter;
    private Long auditingUserId;
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
        context().parent().tell(new BatchSavingCoordinator.GiveMeWork(), self());
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(SingleBatchSaveStartMessage.class, startSave -> doSave(startSave, sender()))
                .matchAny(o -> LOGGER.error("Received Unknown message"))
                .build();
    }

    private void doSave(final SingleBatchSaveStartMessage startSaveMessage, final ActorRef sender) {
        LOGGER.trace("Received SingleBatchSaveStartMessage message: {}", startSaveMessage.toString());

        this.repository = startSaveMessage.getRepository();
        this.converter = startSaveMessage.getConverter();
        this.auditingUserId = startSaveMessage.getAuditingUserId();
        this.batchId = startSaveMessage.getBatchId();
        
        SingleBatchSaveResultMessage result = startSaveMessage.getTxTemplate().execute(
                status -> saveBatch(startSaveMessage.getDtos())
        );

        LOGGER.trace("Sending SingleBatchSaveResultMessage message: {}", result.toString());
        sender.tell(result, self());
    }

    private SingleBatchSaveResultMessage saveBatch(final List<AbstractPersistableDto> batch) {
        List<AbstractAuditable> entities = convertToEntities(batch);

        successCount = repository.batchSave(entities);
        failureCount = getFailureCount(batch.size(), successCount);

        final SingleBatchSaveResultMessage result = new SingleBatchSaveResultMessage(successCount, failureCount, batchId);
        resetCounters();
        return result;
    }

    private int getFailureCount(final int totalCount, final int succcesCount) {
        return totalCount - successCount;
    }

    private void resetCounters() {
        this.successCount = 0;
        this.failureCount = 0;
    }

    private List<AbstractAuditable> convertToEntities(final List<AbstractPersistableDto> batch) {
            return batch.stream()
                    .map(dto -> convertToEntity(dto))
                    .collect(Collectors.toList());
    }

    private AbstractAuditable convertToEntity(final AbstractPersistableDto dto) {
        final AbstractAuditable entity = (AbstractAuditable) converter.getEntity(dto);
        AuditingUtil.configureCreatedByAndTime(entity, auditingUserId);
        return entity;
    }
}
