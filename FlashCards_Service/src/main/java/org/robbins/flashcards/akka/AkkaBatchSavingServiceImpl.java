package org.robbins.flashcards.akka;

import static akka.pattern.Patterns.ask;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.robbins.flashcards.akka.message.Messages;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
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

    @Value("${jdbc.batchsize}")
    private Integer BATCH_SIZE;

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
            final Messages.BatchSaveResultMessage receiptMessage = saveBatchWithAkka(type, facade, dtos);

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

    private Messages.BatchSaveResultMessage saveBatchWithAkka(final String type, final GenericCrudFacade facade,
                                                     final List<AbstractAuditableDto> dtos) throws Exception {

        final BatchLoadingReceiptDto batchLoadingReceiptDto = createBatchLoadingReceipt(type, dtos);
        final Messages.BatchSaveStartMessage startBatchSaveMessage = new Messages.BatchSaveStartMessage(batchLoadingReceiptDto, facade,
                dtos);
        final FiniteDuration duration = FiniteDuration.create(1, TimeUnit.HOURS);
        final ClassTag<Messages.BatchSaveResultMessage> classTag = Util.classTag(Messages.BatchSaveResultMessage.class);
        final Future<Messages.BatchSaveResultMessage> receiptFuture = ask(batchSavingCoordinator, startBatchSaveMessage,
                Timeout.durationToTimeout(duration))
                .mapTo(classTag);
        return Await.result(receiptFuture, duration);
    }

    private BatchLoadingReceiptDto createBatchLoadingReceipt(final String type, final List<AbstractAuditableDto> dtos) {
        BatchLoadingReceiptDto receipt = new BatchLoadingReceiptDto();
        receipt.setType(type);
        receipt.setStartTime(new Date());
        receipt = facade.save(receipt);

        receipt.setBatchSize(BATCH_SIZE);
        receipt.setTotalSize(dtos.size());
        return receipt;
    }

//    private BatchLoadingReceiptDto createBatchLoadingReceiptDto(final String type, final List<AbstractPersistableDto> dtos) {
//        final BatchLoadingReceiptDto receipt = new BatchLoadingReceiptDto();
//        receipt.setType(type);
//        receipt.setBatchSize(BATCH_SIZE);
//        receipt.setTotalSize(dtos.size());
//        receipt.setStartTime(new DateTime().toDate());
//        return receipt;
//    }

    private BatchLoadingReceiptDto completeBatchLoadingReceipt(final Messages.BatchSaveResultMessage receiptMessage) {
        BatchLoadingReceiptDto receipt = receiptMessage.receiptDto();
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

    private ActorRef createBatchSavingCoordinator()
    {
        final SpringExtension akkaSpringExtension = SpringExtension.SpringExtProvider;
        final SpringExtension.SpringExt springExt = akkaSpringExtension.get(actorSystem);
        final Props props = springExt.props("batchSavingCoordinator");
        return actorSystem.actorOf(props);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        batchSavingCoordinator = createBatchSavingCoordinator();
    }

    public Long getAuditingUserId() {
        return auditorAware.getCurrentAuditor();
    }
}
