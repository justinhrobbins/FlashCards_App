
package org.robbins.flashcards.akka.message

import akka.actor.ActorRef
import org.robbins.flashcards.SaveResultStatus
import org.robbins.flashcards.dto.{AbstractAuditableDto, AbstractPersistableDto, BatchLoadingReceiptDto}
import org.robbins.flashcards.facade.base.GenericCrudFacade

object Messages {

  case class BatchSaveResultMessage(receiptDto: BatchLoadingReceiptDto)

  case class BatchSaveStartMessage(receipt: BatchLoadingReceiptDto, facade: GenericCrudFacade[_, _], dtos: java.util.List[AbstractAuditableDto])

  case class SingleBatchSaveResultMessage(successCount: Integer, failureCount: Integer, batchId: Long)

  case class SingleBatchSaveStartMessage(batchId: Long, dtos: java.util.List[AbstractAuditableDto], facade: GenericCrudFacade[_, _])

  case class SingleSaveResultMessage(resultStatus: SaveResultStatus)

  case class SingleSaveStartMessage(dto: AbstractPersistableDto)

  case class WorkQueueItem(batchPartition: java.util.List[AbstractAuditableDto], startMessage: Messages.BatchSaveStartMessage, sender: ActorRef, batchId: Long)

  case class GiveMeWork()
}
