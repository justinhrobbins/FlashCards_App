package org.robbins.flashcards.akka.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.robbins.flashcards.akka.message.BatchSaveResultMessage;
import org.robbins.flashcards.akka.message.BatchSaveStartMessage;
import org.robbins.flashcards.akka.message.SingleBatchSaveResultMessage;
import org.robbins.flashcards.akka.message.SingleBatchSaveStartMessage;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class BatchSavingCoordinator extends AbstractActor
{

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchSavingCoordinator.class);

	private final Map<Long, ActorRef> parents = new ConcurrentHashMap<>();
	private final Map<Long, BatchLoadingReceiptDto> batchesInProgress = new ConcurrentHashMap<>();

	final private List<WorkQueueItem> workQueue = new ArrayList<>();
	final private Map<ActorRef, BatchSaveStartMessage> workInProgress = new HashMap<>();
	final private List<ActorRef> idleWorkers = new ArrayList<>();

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
		IntStream.range(1, 11).forEach(i -> {
			final String actorName = "batch-saving-actor" + i;
			LOGGER.trace("Creating actor: {}", actorName);
			context().actorOf(BatchSavingActor.props(), actorName);
		});
	}

	@Override
	public PartialFunction<Object, BoxedUnit> receive()
	{
		return ReceiveBuilder
				.match(BatchSaveStartMessage.class, this::handleBatchSaveStart)
				.match(SingleBatchSaveResultMessage.class, this::handleSingleBatchSaveResult)
				.match(GiveMeWork.class, w -> scheduleWork(sender()))
				.matchAny(o -> LOGGER.info("Received Unknown message"))
				.build();
	}

	private void handleBatchSaveStart(final BatchSaveStartMessage startMessage)
	{
		LOGGER.trace("Entering handleBatchSaveStart for message: {}", startMessage);
		addMessageToWorkQueue(startMessage, sender());

		if (!idleWorkers.isEmpty()) {
			scheduleWork(idleWorkers.get(0));
		}
	}

	private void handleSingleBatchSaveResult(final SingleBatchSaveResultMessage resultMessage)
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
			final WorkQueueItem workQueueItem = workQueue.remove(0);
			workInProgress.put(worker, workQueueItem.startMessage);
			LOGGER.trace("workInProgress size: {}", workInProgress.size());

			LOGGER.debug("Sending SingleBatchSaveStartMessage message with batch id: '{}' to worker '{}'", workQueueItem.batchId, worker.toString());
			worker.tell(new SingleBatchSaveStartMessage(workQueueItem.batchId, workQueueItem.batchPartition,
					workQueueItem.startMessage.getFacade()), self());
		}

		if (!workQueue.isEmpty() && !idleWorkers.isEmpty())
		{
			scheduleWork(idleWorkers.get(0));
		}
	}

	private void addMessageToWorkQueue(final BatchSaveStartMessage startMessage, final ActorRef sender)
	{
		LOGGER.debug("Received BatchSaveStartMessage message: {}", startMessage.toString());

		final BatchLoadingReceiptDto receipt = startMessage.getReceipt();
		parents.put(receipt.getId(), sender());

		batchesInProgress.put(receipt.getId(), receipt);
		LOGGER.debug("Batches in progress: {}", batchesInProgress.size());

		final List<List<AbstractAuditableDto>> batches = Lists.partition(startMessage.getDtos(), receipt.getBatchSize());
		LOGGER.debug("Splitting batch of {} into {} sub-batches", startMessage.getDtos().size(), batches.size());

		batches.stream()
				.forEach(batch -> workQueue.add(new WorkQueueItem(batch, startMessage, sender, receipt.getId())));
		LOGGER.trace("workQueue size: {}", workQueue.size());
	}

	private void batchSaveFinish(final SingleBatchSaveResultMessage saveResult)
	{
		LOGGER.debug("Received SingleBatchSaveResultMessage message: {}", saveResult.toString());

		final ActorRef parent = parents.get(saveResult.getBatchId());
		final BatchLoadingReceiptDto batch = batchesInProgress.get(saveResult.getBatchId());
		batch.setSuccessCount(batch.getSuccessCount() + saveResult.getSuccessCount());
		batch.setFailureCount(batch.getFailureCount() + saveResult.getFailureCount());

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

		final BatchSaveResultMessage batchSaveResultMessage = new BatchSaveResultMessage(batch);
		LOGGER.debug("Sending BatchSaveResult: {}", batchSaveResultMessage);
		parent.tell(batchSaveResultMessage, self());
	}

	public static class WorkQueueItem
	{
		final Long batchId;
		final ActorRef sender;
		final BatchSaveStartMessage startMessage;
		final List<AbstractAuditableDto> batchPartition;

		public WorkQueueItem(final List<AbstractAuditableDto> batchPartition, final BatchSaveStartMessage startMessage, final ActorRef sender, final Long batchId)
		{
			this.batchPartition = batchPartition;
			this.startMessage = startMessage;
			this.sender = sender;
			this.batchId = batchId;
		}

		@Override
		public boolean equals(final Object o)
		{
			if (this == o)
			{
				return true;
			}
			if (o == null || getClass() != o.getClass())
			{
				return false;
			}

			final WorkQueueItem that = (WorkQueueItem) o;

			if (!batchPartition.equals(that.batchPartition))
			{
				return false;
			}
			if (!batchId.equals(that.batchId))
			{
				return false;
			}
			if (!sender.equals(that.sender))
			{
				return false;
			}
			if (!startMessage.equals(that.startMessage))
			{
				return false;
			}

			return true;
		}

		@Override
		public int hashCode()
		{
			int result = batchId.hashCode();
			result = 31 * result + sender.hashCode();
			result = 31 * result + startMessage.hashCode();
			return result;
		}
	}

	public static class GiveMeWork
	{

	}
}
