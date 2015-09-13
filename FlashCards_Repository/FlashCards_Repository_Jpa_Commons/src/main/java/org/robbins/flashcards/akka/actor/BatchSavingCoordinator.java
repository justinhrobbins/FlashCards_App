package org.robbins.flashcards.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.FromConfig;
import akka.routing.RoundRobinPool;
import org.robbins.flashcards.akka.message.BatchSaveResultMessage;
import org.robbins.flashcards.akka.message.SingleSaveResultMessage;
import org.robbins.flashcards.akka.message.SingleSaveStartMessage;
import org.robbins.flashcards.akka.message.StartBatchSaveMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

@Component("batchSavingCoordinator")
@Scope("prototype")
public class BatchSavingCoordinator extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchSavingCoordinator.class);

    private ActorRef parent;
    private Integer totalItemsToSave;
    private Integer completedTestCount = 0;
    private Integer successCount = 0;
    private Integer failureCount = 0;

    public BatchSavingCoordinator() {
        LOGGER.debug("Creating BatchSavingCoordinator");
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(StartBatchSaveMessage.class, this::batchSaveStart)
                .match(SingleSaveResultMessage.class, this::individualSaveFinish)
                .matchAny(o -> LOGGER.info("Received Unknown message"))
                .build();
    }

    private void batchSaveStart(final StartBatchSaveMessage startMessage) {
        LOGGER.debug("Received StartBatchSaveMessage message: {}", startMessage.toString());

        parent = sender();

        totalItemsToSave = startMessage.getDtos().size();

        ActorRef singleSavingTestingActor = context()
                .actorOf(new RoundRobinPool(8)
                        .props(ItemSaver.props(startMessage.getRepository(), startMessage.getConverter(),
                                startMessage.getAuditingUserId())), "item-saver-router");

        startMessage.getDtos().stream()
                .forEach(dto -> singleSavingTestingActor.tell(new SingleSaveStartMessage(dto), self()));
    }

    private void individualSaveFinish(SingleSaveResultMessage testResult) {
        LOGGER.trace("Received SingleSaveResult message: {}", testResult.toString());

        completedTestCount++;
        if (testResult.getResultStatus().equals(SingleSaveResultMessage.SaveResultStatus.SUCCESS)) {
            successCount++;
        } else {
            failureCount++;
        }

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
