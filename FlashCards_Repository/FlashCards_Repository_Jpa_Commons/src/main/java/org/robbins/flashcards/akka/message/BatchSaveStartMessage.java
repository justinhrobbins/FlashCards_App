package org.robbins.flashcards.akka.message;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class BatchSaveStartMessage implements Serializable {
    private final BatchLoadingReceiptDto receipt;
    private final FlashCardsAppRepository repository;
    private final DtoConverter converter;
    private final Long auditingUserId;
    private final List<AbstractPersistableDto> dtos;
    private final TransactionTemplate txTemplate;
    private final EntityManager em;

    public BatchSaveStartMessage(BatchLoadingReceiptDto receipt, FlashCardsAppRepository repository,
                                 DtoConverter converter, Long auditingUserId,
                                 List<AbstractPersistableDto> dtos, TransactionTemplate txTemplate,
                                 EntityManager em) {
        this.receipt = receipt;
        this.repository = repository;
        this.converter = converter;
        this.auditingUserId = auditingUserId;
        this.dtos = dtos;
        this.txTemplate = txTemplate;
        this.em = em;
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

    public EntityManager getEm() {
        return em;
    }

    @Override
    public String toString() {
        return "BatchSaveStartMessage{}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatchSaveStartMessage that = (BatchSaveStartMessage) o;

        if (!receipt.equals(that.receipt)) return false;
        if (!repository.equals(that.repository)) return false;
        if (!converter.equals(that.converter)) return false;
        if (!auditingUserId.equals(that.auditingUserId)) return false;
        if (!dtos.equals(that.dtos)) return false;
        if (!txTemplate.equals(that.txTemplate)) return false;
        return em.equals(that.em);

    }

    @Override
    public int hashCode() {
        int result = receipt.hashCode();
        result = 31 * result + repository.hashCode();
        result = 31 * result + converter.hashCode();
        result = 31 * result + auditingUserId.hashCode();
        result = 31 * result + dtos.hashCode();
        result = 31 * result + txTemplate.hashCode();
        result = 31 * result + em.hashCode();
        return result;
    }
}