package org.robbins.flashcards.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;
import com.google.common.collect.Lists;
import org.robbins.flashcards.akka.message.BatchSaveResultMessage;
import org.robbins.flashcards.akka.message.BatchSaveStartMessage;
import org.robbins.flashcards.akka.message.SingleBatchSaveResultMessage;
import org.robbins.flashcards.akka.message.SingleBatchSaveStartMessage;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.List;

public class BatchSavingCoordinator extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchSavingCoordinator.class);

    private ActorRef parent;
    private Integer totalItemsToSave;
    private Integer completedTestCount = 0;
    private Integer successCount = 0;
    private Integer failureCount = 0;
    private final int batchSize = 1000;

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

        parent = sender();

        totalItemsToSave = startMessage.getDtos().size();

        ActorRef batchSavingActor = context()
                .actorOf(new RoundRobinPool(4)
                        .props(BatchSavingActor.props(startMessage.getRepository(), startMessage.getConverter(),
                                startMessage.getAuditingUserId())), "batch-saving-router");

        List<List<AbstractPersistableDto>> batches = Lists.partition(startMessage.getDtos(), batchSize);
        LOGGER.debug("Splitting bath of {} into {} sub-batches", startMessage.getDtos().size(), batches.size());

        batches.stream()
                .forEach(batch -> batchSavingActor.tell(new SingleBatchSaveStartMessage(batch,
                        startMessage.getTxTemplate(), startMessage.getEm()), self()));
    }

    private void batchSaveFinish(SingleBatchSaveResultMessage saveResult) {
        LOGGER.debug("Received SingleBatchSaveResultMessage message: {}", saveResult.toString());

        completedTestCount += (saveResult.getSuccessCount() + saveResult.getFailureCount());
        successCount += saveResult.getSuccessCount();
        failureCount += saveResult.getFailureCount();

        if (completedTestCount.equals(totalItemsToSave)) {
            completeBatchSave();
        }
    }

    private void completeBatchSave() {
        BatchSaveResultMessage batchSaveResultMessage = new BatchSaveResultMessage(this.successCount, this.failureCount);
        LOGGER.debug("BatchSaveResult: {}", batchSaveResultMessage);
        parent.tell(batchSaveResultMessage, self());
        context().stop(self());
    }
}
