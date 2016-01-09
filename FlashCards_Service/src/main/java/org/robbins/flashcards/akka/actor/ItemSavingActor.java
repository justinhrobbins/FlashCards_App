package org.robbins.flashcards.akka.actor;

import org.robbins.flashcards.SaveResultStatus;
import org.robbins.flashcards.akka.message.Messages;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.model.util.EntityAuditingUtil;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class ItemSavingActor extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemSavingActor.class);

    private final FlashCardsAppRepository repository;
    private final DtoConverter converter;
    private final Long auditingUserId;

    public ItemSavingActor(final FlashCardsAppRepository repository, final DtoConverter converter, final Long auditingUserId) {
        LOGGER.trace("Creating ItemSaver");

        this.repository = repository;
        this.converter = converter;
        this.auditingUserId = auditingUserId;
    }

    public static Props props(final FlashCardsAppRepository repository,
                              final DtoConverter converter, final Long auditingUserId) {
        return Props.create(ItemSavingActor.class, () -> new ItemSavingActor(repository, converter, auditingUserId));
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(Messages.SingleSaveStartMessage.class, startSave -> doStartSave(startSave, sender()))
                .matchAny(o -> LOGGER.error("Received Unknown message"))
                .build();
    }

    private void doStartSave(final Messages.SingleSaveStartMessage startSaveMessage, final ActorRef sender) {
        LOGGER.trace("Received SingleSaveStart message: {}", startSaveMessage.toString());

        Messages.SingleSaveResultMessage result = saveItem(startSaveMessage.dto());

        LOGGER.trace("Sending SingleSaveResultMessage message: {}", result.toString());
        sender.tell(result, self());
    }

    private Messages.SingleSaveResultMessage saveItem(final AbstractPersistableDto dto) {
        SaveResultStatus resultStatus = SaveResultStatus.SUCCESS;

        try {
            final AbstractAuditable entity = (AbstractAuditable) converter.getEntity(dto);
            EntityAuditingUtil.configureCreatedByAndTime(entity, auditingUserId);
            repository.save(entity);

        } catch (FlashCardsException e) {
            LOGGER.error("Unable to create Dto {}, error: {}", dto.toString(), e.getMessage());
            resultStatus = SaveResultStatus.FAILURE;
        }

        return new Messages.SingleSaveResultMessage(resultStatus);
    }
}
