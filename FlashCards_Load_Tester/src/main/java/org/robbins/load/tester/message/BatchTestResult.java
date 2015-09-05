package org.robbins.load.tester.message;

import org.robbins.flashcards.dto.BulkLoadingReceiptDto;

import java.io.Serializable;

public class BatchTestResult implements Serializable {
    private final String endPointName;
    private final BulkLoadingReceiptDto receipt;

    public BatchTestResult(String endPointName, BulkLoadingReceiptDto receipt) {
        this.endPointName = endPointName;
        this.receipt = receipt;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public BulkLoadingReceiptDto getReceipt() {
        return receipt;
    }

    @Override
    public String toString() {
        return "BatchTestResult{" +
                "endPointName='" + endPointName + '\'' +
                ", receipt=" + receipt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatchTestResult that = (BatchTestResult) o;

        if (!endPointName.equals(that.endPointName)) return false;
        return receipt.equals(that.receipt);

    }

    @Override
    public int hashCode() {
        int result = endPointName.hashCode();
        result = 31 * result + receipt.hashCode();
        return result;
    }
}