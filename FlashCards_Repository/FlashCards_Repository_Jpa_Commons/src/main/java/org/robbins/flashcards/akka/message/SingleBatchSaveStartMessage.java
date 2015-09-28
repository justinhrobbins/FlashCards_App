package org.robbins.flashcards.akka.message;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class SingleBatchSaveStartMessage implements Serializable {
    private final String batchId;
    private final List<AbstractPersistableDto> dtos;
    private final TransactionTemplate txTemplate;
    private final EntityManager em;
    private FlashCardsAppRepository repository;
    private DtoConverter converter;
    private String auditingUserId;

    public SingleBatchSaveStartMessage(final String batchId, final List<AbstractPersistableDto> dtos, final TransactionTemplate txTemplate, final EntityManager em, final FlashCardsAppRepository repository, final DtoConverter converter, final String auditingUserId)
    {
        this.batchId = batchId;
        this.dtos = dtos;
        this.txTemplate = txTemplate;
        this.em = em;
        this.repository = repository;
        this.converter = converter;
        this.auditingUserId = auditingUserId;
    }

    public String getBatchId()
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

    public EntityManager getEm()
    {
        return em;
    }

    public FlashCardsAppRepository getRepository()
    {
        return repository;
    }

    public DtoConverter getConverter()
    {
        return converter;
    }

    public String getAuditingUserId()
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