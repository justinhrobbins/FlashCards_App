package org.robbins.flashcards.akka.message;

import org.robbins.flashcards.dto.BatchLoadingReceiptDto;

import java.io.Serializable;

public class BatchSaveResultMessage implements Serializable {
    private final BatchLoadingReceiptDto receiptDto;

    public BatchSaveResultMessage(BatchLoadingReceiptDto receiptDto) {
        this.receiptDto = receiptDto;
    }

    public BatchLoadingReceiptDto getReceiptDto() {
        return receiptDto;
    }

    @Override
    public String toString() {
        return "BatchSaveResultMessage";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatchSaveResultMessage that = (BatchSaveResultMessage) o;

        return receiptDto.equals(that.receiptDto);

    }

    @Override
    public int hashCode() {
        return receiptDto.hashCode();
    }
}
