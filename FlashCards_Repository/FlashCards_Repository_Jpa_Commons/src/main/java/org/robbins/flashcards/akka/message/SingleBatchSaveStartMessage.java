package org.robbins.flashcards.akka.message;

import java.io.Serializable;
import java.util.List;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.springframework.transaction.support.TransactionTemplate;

public class SingleBatchSaveStartMessage implements Serializable {
    private final Long batchId;
    private final List<AbstractPersistableDto> dtos;
    private final TransactionTemplate txTemplate;
    private FlashCardsAppRepository repository;
    private DtoConverter converter;
    private Long auditingUserId;

    public SingleBatchSaveStartMessage(final Long batchId, final List<AbstractPersistableDto> dtos,
            final TransactionTemplate txTemplate, final FlashCardsAppRepository repository,
            final DtoConverter converter, final Long auditingUserId)
    {
        this.batchId = batchId;
        this.dtos = dtos;
        this.txTemplate = txTemplate;
        this.repository = repository;
        this.converter = converter;
        this.auditingUserId = auditingUserId;
    }

    public Long getBatchId()
    {
        return batchId;
    }

    public List<AbstractPersistableDto> getDtos()
    {
        return dtos;
    }

    public TransactionTemplate getTxTemplate()
    {
        return txTemplate;
    }

    public FlashCardsAppRepository getRepository()
    {
        return repository;
    }

    public DtoConverter getConverter()
    {
        return converter;
    }

    public Long getAuditingUserId()
    {
        return auditingUserId;
    }

    @Override
    public String toString() {
        return "SingleBatchSaveStartMessage{" +
                "batchId='" + batchId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleBatchSaveStartMessage that = (SingleBatchSaveStartMessage) o;

        return batchId.equals(that.batchId);

    }

    @Override
    public int hashCode() {
        return batchId.hashCode();
    }
}