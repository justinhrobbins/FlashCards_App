package org.robbins.flashcards.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.Util;
import akka.util.Timeout;
import org.joda.time.DateTime;
import org.robbins.flashcards.akka.message.BatchSaveResultMessage;
import org.robbins.flashcards.akka.message.StartBatchSaveMessage;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.model.BatchLoadingReceipt;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.robbins.flashcards.repository.util.dozer.DateTimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;


@Component
public class AkkaBatchSavingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AkkaBatchSavingService.class);

    @Inject
    private ActorSystem actorSystem;

    @Inject
    @Qualifier("batchLoadingReceiptDtoConverter")
    private DtoConverter<BatchLoadingReceiptDto, BatchLoadingReceipt> batchReceiptConverter;

    public BatchLoadingReceiptDto save(final FlashCardsAppRepository repository, final DtoConverter converter,
                                       final String auditingUserId,  final List<AbstractPersistableDto> entities) {
        LOGGER.debug("Sending StartBatchSaveMessage message to BatchSavingCoordinator");

        final Date startTime = new DateTime().toDate();
        final String type = entities.iterator().next().getClass().getSimpleName();

        final ActorRef batcSavingCoordinator = actorSystem.actorOf(
                SpringExtension.SpringExtProvider.get(actorSystem).props("batchSavingCoordinator"));

        final StartBatchSaveMessage startBatchSaveMessage = new StartBatchSaveMessage(repository, converter, auditingUserId, entities);
        final FiniteDuration duration = FiniteDuration.create(1, TimeUnit.HOURS);
        final ClassTag<BatchSaveResultMessage> classTag = Util.classTag(BatchSaveResultMessage.class);
        final Future<BatchSaveResultMessage> receiptFuture = ask(batcSavingCoordinator, startBatchSaveMessage,
                Timeout.durationToTimeout(duration))
                .mapTo(classTag);

        try {
            final BatchSaveResultMessage receipt = Await.result(receiptFuture, duration);
            return new BatchLoadingReceiptDto(type, receipt.getSuccessCount(),
                    receipt.getFailureCount(), startTime, new DateTime().toDate());
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
