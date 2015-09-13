package org.robbins.flashcards.akka.message;

import java.io.Serializable;

public class BatchSaveResultMessage implements Serializable {
    private final Integer successCount;
    private final Integer failureCount;

    public BatchSaveResultMessage(Integer successCount, Integer failureCount) {
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    @Override
    public String toString() {
        return "BatchSaveResult{" +
                "successCount=" + successCount +
                ", failureCount=" + failureCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatchSaveResultMessage that = (BatchSaveResultMessage) o;

        if (!successCount.equals(that.successCount)) return false;
        return failureCount.equals(that.failureCount);

    }

    @Override
    public int hashCode() {
        int result = successCount.hashCode();
        result = 31 * result + failureCount.hashCode();
        return result;
    }
}
