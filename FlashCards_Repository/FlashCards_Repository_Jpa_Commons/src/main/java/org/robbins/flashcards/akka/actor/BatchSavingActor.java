package org.robbins.flashcards.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.robbins.flashcards.akka.message.SingleBatchSaveResultMessage;
import org.robbins.flashcards.akka.message.SingleBatchSaveStartMessage;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.model.util.AuditingUtil;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import javax.persistence.EntityManager;
import java.util.List;

public class BatchSavingActor extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchSavingActor.class);

    private final FlashCardsAppRepository repository;
    private final DtoConverter converter;
    private final String auditingUserId;
    private Integer successCount = 0;
    private Integer failureCount = 0;

    public BatchSavingActor(final FlashCardsAppRepository repository, final DtoConverter converter, final String auditingUserId) {
        LOGGER.debug("Creating BatchSavingActor");

        this.repository = repository;
        this.converter = converter;
        this.auditingUserId = auditingUserId;
    }

    public static Props props(final FlashCardsAppRepository repository,
                              final DtoConverter converter, final String auditingUserId) {
        return Props.create(BatchSavingActor.class, () -> new BatchSavingActor(repository, converter, auditingUserId));
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(SingleBatchSaveStartMessage.class, startSave -> doStartSave(startSave, sender()))
                .matchAny(o -> LOGGER.error("Received Unknown message"))
                .build();
    }

    private void doStartSave(final SingleBatchSaveStartMessage startSaveMessage, final ActorRef sender) {
        LOGGER.trace("Received SingleBatchSaveStartMessage message: {}", startSaveMessage.toString());

        SingleBatchSaveResultMessage result = startSaveMessage.getTxTemplate().execute(
                status -> saveBatch(startSaveMessage.getEm(), startSaveMessage.getDtos())
        );

        LOGGER.trace("Sending SingleBatchSaveResultMessage message: {}", result.toString());
        sender.tell(result, self());
    }

    private SingleBatchSaveResultMessage saveBatch(final EntityManager em,
                                                   final List<AbstractPersistableDto> batch) {
        batch.stream().forEach(dto -> saveItem(dto));

        final SingleBatchSaveResultMessage result = new SingleBatchSaveResultMessage(successCount, failureCount);
        resetCounters();
        return result;
    }

    private void resetCounters() {
        this.successCount = 0;
        this.failureCount = 0;
    }

    private void saveItem(final AbstractPersistableDto dto) {
        try {
            final AbstractAuditable entity = (AbstractAuditable) converter.getEntity(dto);
            AuditingUtil.configureCreatedByAndTime(entity, auditingUserId);
            repository.save(entity);
            successCount++;

        } catch (FlashcardsException e) {
            LOGGER.error("Unable to create Dto {}, error: {}", dto.toString(), e.getMessage());
            failureCount++;
        }
    }
}
