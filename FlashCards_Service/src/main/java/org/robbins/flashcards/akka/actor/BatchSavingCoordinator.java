package org.robbins.flashcards.akka.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.robbins.flashcards.akka.message.Messages;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

@Scope("prototype")
@Component("batchSavingCoordinator")
public class BatchSavingCoordinator extends AbstractActor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchSavingCoordinator.class);

	@Value("${akka.workerCount}")
	private Integer WORKER_COUNT;

	private final Map<Long, ActorRef> parents = new ConcurrentHashMap<>();
	private final Map<Long, BatchLoadingReceiptDto> batchesInProgress = new ConcurrentHashMap<>();

	private final List<Messages.WorkQueueItem> workQueue = new ArrayList<>();
	private final Map<ActorRef, Messages.BatchSaveStartMessage> workInProgress = new HashMap<>();
	private final List<ActorRef> idleWorkers = new ArrayList<>();

	public BatchSavingCoordinator()
	{
		LOGGER.debug("Creating BatchSavingCoordinator");
	}

	public static Props props()
	{
		return Props.create(BatchSavingCoordinator.class, BatchSavingCoordinator::new);
	}

	@Override
	public void preStart() throws Exception
	{
		super.preStart();
		createBatchSavingActors();
	}

	private void createBatchSavingActors()
	{
		IntStream.range(0, WORKER_COUNT).forEach(i -> {
			final String actorName = "batch-saving-actor" + i;
			LOGGER.trace("Creating actor: {}", actorName);
			context().actorOf(BatchSavingActor.props(), actorName);
		});
	}

	@Override
	public PartialFunction<Object, BoxedUnit> receive()
	{
		return ReceiveBuilder
				.match(Messages.BatchSaveStartMessage.class, this::handleBatchSaveStart)
				.match(Messages.SingleBatchSaveResultMessage.class, this::handleSingleBatchSaveResult)
				.match(Messages.GiveMeWork.class, w -> scheduleWork(sender()))
				.matchAny(o -> LOGGER.info("Received Unknown message"))
				.build();
	}

	private void handleBatchSaveStart(final Messages.BatchSaveStartMessage startMessage)
	{
		LOGGER.trace("Entering handleBatchSaveStart for message: {}", startMessage);
		addMessageToWorkQueue(startMessage, sender());

		if (!idleWorkers.isEmpty()) {
			scheduleWork(idleWorkers.get(0));
		}
	}

	private void handleSingleBatchSaveResult(final Messages.SingleBatchSaveResultMessage resultMessage)
	{
		LOGGER.trace("Entering handleSingleBatchSaveResult for message: {}", resultMessage);

		batchSaveFinish(resultMessage);

		// worker has finished work and is eligible for more work
		scheduleWork(sender());
		workInProgress.remove(sender());
	}

	private void scheduleWork(final ActorRef worker)
	{
		idleWorkers.remove(worker);

		// if there is no work, add the worker to the Idle Worker list
		if (workQueue.isEmpty())
		{
			idleWorkers.add(worker);
		}
		else
		{
			// take the first item out of the Work queue and add it to the Work In Progress queue
			final Messages.WorkQueueItem workQueueItem = workQueue.remove(0);
			workInProgress.put(worker, workQueueItem.startMessage());
			LOGGER.trace("workInProgress size: {}", workInProgress.size());

			LOGGER.debug("Sending SingleBatchSaveStartMessage message with batch id: '{}' to worker '{}'", workQueueItem.batchId(), worker.toString());
			worker.tell(new Messages.SingleBatchSaveStartMessage(workQueueItem.batchId(), workQueueItem.batchPartition(),
					workQueueItem.startMessage().facade()), self());
		}

		if (!workQueue.isEmpty() && !idleWorkers.isEmpty())
		{
			scheduleWork(idleWorkers.get(0));
		}
	}

	private void addMessageToWorkQueue(final Messages.BatchSaveStartMessage startMessage, final ActorRef sender)
	{
		LOGGER.debug("Received BatchSaveStartMessage message: {}", startMessage.toString());

		final BatchLoadingReceiptDto receipt = startMessage.receipt();
		parents.put(receipt.getId(), sender());

		batchesInProgress.put(receipt.getId(), receipt);
		LOGGER.debug("Batches in progress: {}", batchesInProgress.size());

		final List<List<AbstractAuditableDto>> batches = Lists.partition(startMessage.dtos(), receipt.getBatchSize());
		LOGGER.debug("Splitting batch of {} into {} sub-batches", startMessage.dtos().size(), batches.size());

		batches.stream()
				.forEach(batch -> workQueue.add(new Messages.WorkQueueItem(batch, startMessage, sender, receipt.getId())));
		LOGGER.trace("workQueue size: {}", workQueue.size());
	}

	private void batchSaveFinish(final Messages.SingleBatchSaveResultMessage saveResult)
	{
		LOGGER.debug("Received SingleBatchSaveResultMessage message: {}", saveResult.toString());

		final ActorRef parent = parents.get(saveResult.batchId());
		final BatchLoadingReceiptDto batch = batchesInProgress.get(saveResult.batchId());
		batch.setSuccessCount(batch.getSuccessCount() + saveResult.successCount());
		batch.setFailureCount(batch.getFailureCount() + saveResult.failureCount());

		if (isBatchComplete(batch))
		{
			completeBatchSave(batch, parent);
		}
	}

	private boolean isBatchComplete(final BatchLoadingReceiptDto batch)
	{
		return batch.getTotalSize() == (batch.getSuccessCount() + batch.getFailureCount());
	}

	private void completeBatchSave(final BatchLoadingReceiptDto batch, final ActorRef parent)
	{
		batchesInProgress.remove(batch.getId());

		final Messages.BatchSaveResultMessage batchSaveResultMessage = new Messages.BatchSaveResultMessage(batch);
		LOGGER.debug("Sending BatchSaveResult: {}", batchSaveResultMessage);
		parent.tell(batchSaveResultMessage, self());
	}
}
