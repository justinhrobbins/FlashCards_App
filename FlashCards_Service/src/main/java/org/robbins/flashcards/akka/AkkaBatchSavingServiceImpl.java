package org.robbins.flashcards.akka;

import static akka.pattern.Patterns.ask;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.robbins.flashcards.akka.actor.BatchSavingCoordinator;
import org.robbins.flashcards.akka.message.BatchSaveResultMessage;
import org.robbins.flashcards.akka.message.BatchSaveStartMessage;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.BatchReceiptFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.repository.auditing.AuditingAwareUser;
import org.robbins.flashcards.util.DtoAuditingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.Util;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag;


@Component
public class AkkaBatchSavingServiceImpl implements InitializingBean, AkkaBatchSavingService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AkkaBatchSavingServiceImpl.class);

    @Inject
    private ActorSystem actorSystem;

    @Inject
    @Qualifier("batchReceiptRepositoryFacade")
    private BatchReceiptFacade facade;

    @Inject
    private AuditingAwareUser auditorAware;

    private ActorRef batchSavingCoordinator;

    @Override
    public BatchLoadingReceiptDto save(final GenericCrudFacade facade, final List<AbstractAuditableDto> dtos) {
        LOGGER.debug("Sending StartBatchSaveMessage message to BatchSavingCoordinator for {} dtos", dtos.size());

        final String type = dtos.iterator().next().getClass().getSimpleName();

        // need to marked Created By here because the auditing user won't be available in the Akka system
        configureCreatedBy(dtos);

        try {
            final BatchSaveResultMessage receiptMessage = saveBatchWithAkka(type, facade, dtos);

            LOGGER.debug("Batch save complete: {}", receiptMessage);
            return completeBatchLoadingReceipt(receiptMessage);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    private void configureCreatedBy(final List<AbstractAuditableDto> dtos) {
        dtos.forEach(dto -> DtoAuditingUtil.configureCreatedByAndTime(dto, getAuditingUserId()));
    }

    private BatchSaveResultMessage saveBatchWithAkka(final String type, final GenericCrudFacade facade,
                                                     final List<AbstractAuditableDto> dtos) throws Exception {

        final BatchLoadingReceiptDto batchLoadingReceiptDto = createBatchLoadingReceipt(type, dtos);
        final BatchSaveStartMessage startBatchSaveMessage = new BatchSaveStartMessage(batchLoadingReceiptDto, facade,
                dtos);
        final FiniteDuration duration = FiniteDuration.create(1, TimeUnit.HOURS);
        final ClassTag<BatchSaveResultMessage> classTag = Util.classTag(BatchSaveResultMessage.class);
        final Future<BatchSaveResultMessage> receiptFuture = ask(batchSavingCoordinator, startBatchSaveMessage,
                Timeout.durationToTimeout(duration))
                .mapTo(classTag);
        return Await.result(receiptFuture, duration);
    }

    private BatchLoadingReceiptDto createBatchLoadingReceipt(final String type, final List<AbstractAuditableDto> dtos) {
        BatchLoadingReceiptDto receipt = new BatchLoadingReceiptDto();
        receipt.setType(type);
        receipt.setStartTime(new Date());
        receipt = facade.save(receipt);

        receipt.setBatchSize(1000);
        receipt.setTotalSize(dtos.size());
        return receipt;
    }

//    private BatchLoadingReceiptDto createBatchLoadingReceiptDto(final String type, final List<AbstractPersistableDto> dtos) {
//        final BatchLoadingReceiptDto receipt = new BatchLoadingReceiptDto();
//        receipt.setType(type);
//        // TODO: replace with injected property
//        receipt.setBatchSize(1000);
//        receipt.setTotalSize(dtos.size());
//        receipt.setStartTime(new DateTime().toDate());
//        return receipt;
//    }

    private BatchLoadingReceiptDto completeBatchLoadingReceipt(final BatchSaveResultMessage receiptMessage) {
        BatchLoadingReceiptDto receipt = receiptMessage.getReceiptDto();
        receipt.setEndTime(new Date());
        receipt = facade.save(receipt);
        return receipt;
    }

//    @Transactional
//    public BatchLoadingReceiptDto createReceipt(final BatchSaveResultMessage receiptMessage, final Long auditingUserId) {
//        final BatchLoadingReceiptDto receiptDto = receiptMessage.getReceiptDto();
//        BatchLoadingReceipt receipt = new BatchLoadingReceipt(receiptDto.getType(), receiptDto.getSuccessCount(),
//        receiptDto.getFailureCount(), receiptDto.getStartTime(), new DateTime().toDate());
//        AuditingUtil.configureCreatedByAndTime(receipt, auditingUserId);
//        receipt = receiptRepository.save(receipt);
//        return batchReceiptConverter.getDto(receipt);
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        batchSavingCoordinator = actorSystem.actorOf(BatchSavingCoordinator.props());
    }

    public Long getAuditingUserId() {
        return auditorAware.getCurrentAuditor();
    }
}
