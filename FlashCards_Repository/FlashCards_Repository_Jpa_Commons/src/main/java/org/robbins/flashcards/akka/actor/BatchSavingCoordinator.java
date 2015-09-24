package org.robbins.flashcards.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.google.common.collect.Lists;
import org.robbins.flashcards.akka.message.BatchSaveResultMessage;
import org.robbins.flashcards.akka.message.BatchSaveStartMessage;
import org.robbins.flashcards.akka.message.SingleBatchSaveResultMessage;
import org.robbins.flashcards.akka.message.SingleBatchSaveStartMessage;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BatchSavingCoordinator extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchSavingCoordinator.class);

    private final Map<String, ActorRef> parents = new ConcurrentHashMap<>();
    private final Map<String, BatchLoadingReceiptDto> batchesInProgress = new ConcurrentHashMap<>();

    public BatchSavingCoordinator() {
        LOGGER.debug("Creating BatchSavingCoordinator");
    }

    public static Props props() {
        return Props.create(BatchSavingCoordinator.class, BatchSavingCoordinator::new);
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(BatchSaveStartMessage.class, this::batchSaveStart)
                .match(SingleBatchSaveResultMessage.class, this::batchSaveFinish)
                .matchAny(o -> LOGGER.info("Received Unknown message"))
                .build();
    }

    private void batchSaveStart(final BatchSaveStartMessage startMessage) {
        LOGGER.debug("Received BatchSaveStartMessage message: {}", startMessage.toString());

        final BatchLoadingReceiptDto receipt = startMessage.getReceipt();
        parents.put(receipt.getId(), sender());
        batchesInProgress.put(receipt.getId(), receipt);

        List<List<AbstractPersistableDto>> batches = Lists.partition(startMessage.getDtos(), receipt.getBatchSize());
        LOGGER.debug("Splitting batch of {} into {} sub-batches", startMessage.getDtos().size(), batches.size());

        batches.stream()
                .forEach(batch -> {
                    final ActorRef batchSavingActor = context()
                            .actorOf(BatchSavingActor.props(startMessage.getRepository(), startMessage.getConverter(),
                                    startMessage.getAuditingUserId()));

                    batchSavingActor.tell(new SingleBatchSaveStartMessage(receipt.getId() ,batch,
                            startMessage.getTxTemplate(), startMessage.getEm()), self());
                });
    }

    private void batchSaveFinish(final SingleBatchSaveResultMessage saveResult) {
        LOGGER.debug("Received SingleBatchSaveResultMessage message: {}", saveResult.toString());

        final ActorRef parent = parents.get(saveResult.getBatchId());
        final BatchLoadingReceiptDto batch = batchesInProgress.get(saveResult.getBatchId());
        batch.setSuccessCount(batch.getSuccessCount() + saveResult.getSuccessCount());
        batch.setFailureCount(batch.getFailureCount() + saveResult.getFailureCount());

        if (isBatchComplete(batch)) {
            completeBatchSave(batch, parent);
        }
    }

    private boolean isBatchComplete(final BatchLoadingReceiptDto batch)
    {
        return batch.getTotalSize() == (batch.getSuccessCount() + batch.getFailureCount());
    }

    private void completeBatchSave(final BatchLoadingReceiptDto batch, final ActorRef parent) {
        final BatchSaveResultMessage batchSaveResultMessage = new BatchSaveResultMessage(batch);
        LOGGER.debug("Sending BatchSaveResult: {}", batchSaveResultMessage);
        parent.tell(batchSaveResultMessage, self());
    }
}
