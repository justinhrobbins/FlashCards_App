package org.robbins.flashcards.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.robbins.flashcards.akka.message.SingleSaveResultMessage;
import org.robbins.flashcards.akka.message.SingleSaveStartMessage;
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

public class ItemSavingActor extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemSavingActor.class);

    private final FlashCardsAppRepository repository;
    private final DtoConverter converter;
    private final String auditingUserId;

    public ItemSavingActor(final FlashCardsAppRepository repository, final DtoConverter converter, final String auditingUserId) {
        LOGGER.trace("Creating ItemSaver");

        this.repository = repository;
        this.converter = converter;
        this.auditingUserId = auditingUserId;
    }

    public static Props props(final FlashCardsAppRepository repository,
                              final DtoConverter converter, final String auditingUserId) {
        return Props.create(ItemSavingActor.class, () -> new ItemSavingActor(repository, converter, auditingUserId));
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

    private SingleSaveResultMessage saveItem(final AbstractPersistableDto dto) {
        SingleSaveResultMessage.SaveResultStatus resultStatus = SingleSaveResultMessage.SaveResultStatus.SUCCESS;

        try {
            final AbstractAuditable entity = (AbstractAuditable) converter.getEntity(dto);
            AuditingUtil.configureCreatedByAndTime(entity, auditingUserId);
            repository.save(entity);

        } catch (FlashcardsException e) {
            LOGGER.error("Unable to create Dto {}, error: {}", dto.toString(), e.getMessage());
            resultStatus = SingleSaveResultMessage.SaveResultStatus.FAILURE;
        }

        return new SingleSaveResultMessage(resultStatus);
    }
}
