package org.robbins.flashcards.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.joda.time.DateTime;
import org.robbins.flashcards.akka.message.SingleSaveResultMessage;
import org.robbins.flashcards.akka.message.SingleSaveStartMessage;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class ItemSaver extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemSaver.class);

    private final FlashCardsAppRepository repository;
    private final DtoConverter converter;
    private final String auditingUserId;

    public ItemSaver(final FlashCardsAppRepository repository, final DtoConverter converter, final String auditingUserId) {
        LOGGER.trace("Creating LoadTestingActor");

        this.repository = repository;
        this.converter = converter;
        this.auditingUserId = auditingUserId;
    }

    public static Props props(final FlashCardsAppRepository repository,
                              final DtoConverter converter, final String auditingUserId) {
        return Props.create(ItemSaver.class, () -> new ItemSaver(repository, converter, auditingUserId));
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(SingleSaveStartMessage.class, startSave -> doStartSave(startSave, sender()))
                .matchAny(o -> LOGGER.error("Received Unknown message"))
                .build();
    }

    private void doStartSave(final SingleSaveStartMessage startSaveMessage, final ActorRef sender) {
        LOGGER.trace("Received SingleSaveStart message: {}", startSaveMessage.toString());

        SingleSaveResultMessage result = saveItem(startSaveMessage.getDto());

        LOGGER.trace("Sending SingleSaveResultMessage message: {}", result.toString());
        sender.tell(result, self());
    }

    private <D extends AbstractAuditableDto> SingleSaveResultMessage saveItem(final AbstractPersistableDto dto) {
        SingleSaveResultMessage.SaveResultStatus resultStatus = SingleSaveResultMessage.SaveResultStatus.SUCCESS;

        try {
            final AbstractAuditable entity = (AbstractAuditable) converter.getEntity(dto);
            configureCreatedByAndTime(entity);
            repository.save(entity);

        } catch (FlashcardsException e) {
            LOGGER.error("Unable to create Dto {}, error: {}", dto.toString(), e.getMessage());
            resultStatus = SingleSaveResultMessage.SaveResultStatus.FAILURE;
        }

        return new SingleSaveResultMessage(resultStatus);
    }

    private void configureCreatedByAndTime(final AbstractAuditable entity) {
        entity.setCreatedBy(auditingUserId);
        entity.setCreatedDate(new DateTime());
    }
}
