package org.robbins.flashcards.akka.message;

import java.io.Serializable;

public class SingleBatchSaveResultMessage implements Serializable {
    private final Integer successCount;
    private final Integer failureCount;
    private final Long batchId;

    public SingleBatchSaveResultMessage(Integer successCount, Integer failureCount, Long batchId) {
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.batchId = batchId;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public Long getBatchId() {
        return batchId;
    }

    @Override
    public String toString() {
        return "SingleBatchSaveResultMessage{" +
                "successCount=" + successCount +
                ", failureCount=" + failureCount +
                ", batchId='" + batchId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleBatchSaveResultMessage that = (SingleBatchSaveResultMessage) o;

        if (!successCount.equals(that.successCount)) return false;
        if (!failureCount.equals(that.failureCount)) return false;
        return batchId.equals(that.batchId);

    }

    @Override
    public int hashCode() {
        int result = successCount.hashCode();
        result = 31 * result + failureCount.hashCode();
        result = 31 * result + batchId.hashCode();
        return result;
    }
}
