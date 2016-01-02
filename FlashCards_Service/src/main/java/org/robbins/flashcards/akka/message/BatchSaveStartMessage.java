package org.robbins.flashcards.akka.message;

import java.io.Serializable;
import java.util.List;

import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.facade.base.GenericCrudFacade;

public class BatchSaveStartMessage implements Serializable {
    private final BatchLoadingReceiptDto receipt;
    private final GenericCrudFacade facade;
    private final List<AbstractAuditableDto> dtos;

    public BatchSaveStartMessage(final BatchLoadingReceiptDto receipt, GenericCrudFacade facade,
            final List<AbstractAuditableDto> dtos) {
        this.receipt = receipt;
        this.facade = facade;
        this.dtos = dtos;
    }

    public BatchLoadingReceiptDto getReceipt() {
        return receipt;
    }

    public GenericCrudFacade getFacade()
    {
        return facade;
    }

    public List<AbstractAuditableDto> getDtos() {
        return dtos;
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

        if (!dtos.equals(that.dtos))
        {
            return false;
        }
        if (!receipt.equals(that.receipt))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = receipt.hashCode();
        result = 31 * result + dtos.hashCode();
        return result;
    }
}