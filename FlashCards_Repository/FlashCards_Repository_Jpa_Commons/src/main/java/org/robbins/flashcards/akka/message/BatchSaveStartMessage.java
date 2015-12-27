package org.robbins.flashcards.akka.message;

import java.io.Serializable;
import java.util.List;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.springframework.transaction.support.TransactionTemplate;

public class BatchSaveStartMessage implements Serializable {
    private final BatchLoadingReceiptDto receipt;
    private final FlashCardsAppRepository repository;
    private final DtoConverter converter;
    private final Long auditingUserId;
    private final List<AbstractPersistableDto> dtos;
    private final TransactionTemplate txTemplate;

    public BatchSaveStartMessage(BatchLoadingReceiptDto receipt, FlashCardsAppRepository repository,
                                 DtoConverter converter, Long auditingUserId,
                                 List<AbstractPersistableDto> dtos, TransactionTemplate txTemplate) {
        this.receipt = receipt;
        this.repository = repository;
        this.converter = converter;
        this.auditingUserId = auditingUserId;
        this.dtos = dtos;
        this.txTemplate = txTemplate;
    }

    public BatchLoadingReceiptDto getReceipt() {
        return receipt;
    }

    public FlashCardsAppRepository getRepository() {
        return repository;
    }

    public DtoConverter getConverter() {
        return converter;
    }

    public Long getAuditingUserId() {
        return auditingUserId;
    }

    public List<AbstractPersistableDto> getDtos() {
        return dtos;
    }

    public TransactionTemplate getTxTemplate() {
        return txTemplate;
    }

    @Override
    public String toString() {
        return "BatchSaveStartMessage{}";
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

        final BatchSaveStartMessage that = (BatchSaveStartMessage) o;

        if (!auditingUserId.equals(that.auditingUserId))
        {
            return false;
        }
        if (!converter.equals(that.converter))
        {
            return false;
        }
        if (!dtos.equals(that.dtos))
        {
            return false;
        }
        if (!receipt.equals(that.receipt))
        {
            return false;
        }
        if (!repository.equals(that.repository))
        {
            return false;
        }
        if (!txTemplate.equals(that.txTemplate))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = receipt.hashCode();
        result = 31 * result + repository.hashCode();
        result = 31 * result + converter.hashCode();
        result = 31 * result + auditingUserId.hashCode();
        result = 31 * result + dtos.hashCode();
        result = 31 * result + txTemplate.hashCode();
        return result;
    }
}