package org.robbins.flashcards.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.Util;
import akka.util.Timeout;
import org.joda.time.DateTime;
import org.robbins.flashcards.akka.actor.BatchSavingCoordinator;
import org.robbins.flashcards.akka.message.BatchSaveResultMessage;
import org.robbins.flashcards.akka.message.BatchSaveStartMessage;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.model.BatchLoadingReceipt;
import org.robbins.flashcards.model.util.AuditingUtil;
import org.robbins.flashcards.repository.BatchLoadingReceiptRepository;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
    private PlatformTransactionManager txManager;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private BatchLoadingReceiptRepository<BatchLoadingReceipt, String> receiptRepository;

    @Inject
    @Qualifier("batchLoadingReceiptDtoConverter")
    private DtoConverter<BatchLoadingReceiptDto, BatchLoadingReceipt> batchReceiptConverter;

    public BatchLoadingReceiptDto save(final FlashCardsAppRepository repository, final DtoConverter converter,
                                       final String auditingUserId,  final List<AbstractPersistableDto> entities) {
        LOGGER.debug("Sending StartBatchSaveMessage message to BatchSavingCoordinator for {} entities", entities.size());

        final Date startTime = new DateTime().toDate();
        final String type = entities.iterator().next().getClass().getSimpleName();

        try {
            final BatchSaveResultMessage receiptMessage = saveBatchWithAkka(repository, converter, auditingUserId, entities);

            return createReceipt(receiptMessage, type, startTime, auditingUserId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    private BatchSaveResultMessage saveBatchWithAkka(final FlashCardsAppRepository repository, final DtoConverter converter,
                                                     final String auditingUserId,  final List<AbstractPersistableDto> entities) throws Exception {
        final ActorRef batchSavingCoordinator = actorSystem.actorOf(BatchSavingCoordinator.props());

        final BatchSaveStartMessage startBatchSaveMessage = new BatchSaveStartMessage(repository, converter,
                auditingUserId, entities,
                new TransactionTemplate(txManager), em);
        final FiniteDuration duration = FiniteDuration.create(1, TimeUnit.HOURS);
        final ClassTag<BatchSaveResultMessage> classTag = Util.classTag(BatchSaveResultMessage.class);
        final Future<BatchSaveResultMessage> receiptFuture = ask(batchSavingCoordinator, startBatchSaveMessage,
                Timeout.durationToTimeout(duration))
                .mapTo(classTag);
        return Await.result(receiptFuture, duration);
    }

    @Transactional
    public BatchLoadingReceiptDto createReceipt(final BatchSaveResultMessage receiptMessage, final String type,
                                                 final Date startTime, final String auditingUserId) {
        BatchLoadingReceipt receipt = new BatchLoadingReceipt(type, receiptMessage.getSuccessCount(),
                receiptMessage.getFailureCount(), startTime, new DateTime().toDate());
        AuditingUtil.configureCreatedByAndTime(receipt, auditingUserId);
        receipt = receiptRepository.save(receipt);
        return batchReceiptConverter.getDto(receipt);
    }
}
