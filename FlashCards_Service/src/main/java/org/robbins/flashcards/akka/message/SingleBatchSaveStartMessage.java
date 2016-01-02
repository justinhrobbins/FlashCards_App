package org.robbins.flashcards.akka.message;

import java.io.Serializable;
import java.util.List;

import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.facade.base.GenericCrudFacade;

public class SingleBatchSaveStartMessage implements Serializable {
    private final Long batchId;
    private final List<AbstractAuditableDto> dtos;
    private final GenericCrudFacade facade;

    public SingleBatchSaveStartMessage(final Long batchId, final List<AbstractAuditableDto> dtos,
            final GenericCrudFacade facade)
    {
        this.batchId = batchId;
        this.dtos = dtos;
        this.facade = facade;
    }

    public Long getBatchId()
    {
        return batchId;
    }

    public List<AbstractAuditableDto> getDtos()
    {
        return dtos;
    }

    public GenericCrudFacade getFacade()
    {
        return facade;
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