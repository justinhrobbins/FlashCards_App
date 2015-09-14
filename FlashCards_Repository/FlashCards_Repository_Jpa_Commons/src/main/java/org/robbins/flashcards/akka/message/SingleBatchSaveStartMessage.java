package org.robbins.flashcards.akka.message;

import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class SingleBatchSaveStartMessage implements Serializable {
    private final List<AbstractPersistableDto> dtos;
    private final TransactionTemplate txTemplate;
    private final EntityManager em;

    public SingleBatchSaveStartMessage(List<AbstractPersistableDto> dtos, TransactionTemplate txTemplate, EntityManager em) {
        this.dtos = dtos;
        this.txTemplate = txTemplate;
        this.em = em;
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
        return "SingleBatchSaveStartMessage{}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleBatchSaveStartMessage that = (SingleBatchSaveStartMessage) o;

        if (!dtos.equals(that.dtos)) return false;
        if (!txTemplate.equals(that.txTemplate)) return false;
        return em.equals(that.em);

    }

    @Override
    public int hashCode() {
        int result = dtos.hashCode();
        result = 31 * result + txTemplate.hashCode();
        result = 31 * result + em.hashCode();
        return result;
    }
}