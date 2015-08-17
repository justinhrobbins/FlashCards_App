package org.robbins.load.tester.message;

import java.io.Serializable;

public class LoadTestResult implements Serializable {
    private final Long endPointInvocationCount;
    private final String endPointName;
    private final Long successCount;
    private final Long failureCount;
    private final Long loadTestDuration;

    public LoadTestResult(final Long endPointInvocationCount, final String endPointName,
                          final Long successCount, final Long failureCount, final Long loadTestDuration) {
        this.endPointInvocationCount = endPointInvocationCount;
        this.endPointName = endPointName;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.loadTestDuration = loadTestDuration;
    }

    public Long getEndPointInvocationCount() {
        return endPointInvocationCount;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public Long getSuccessCount() {
        return successCount;
    }

    public Long getFailureCount() {
        return failureCount;
    }

    public Long getLoadTestDuration() {
        return loadTestDuration;
    }

    @Override
    public String toString() {
        return "LoadTestResult{" +
                "endPointInvocationCount=" + endPointInvocationCount +
                ", endPointName='" + endPointName + '\'' +
                ", successCount=" + successCount +
                ", failureCount=" + failureCount +
                ", loadTestDuration=" + loadTestDuration +
                ", averageDuration=" + (Math.ceil(loadTestDuration/endPointInvocationCount)) + " milliseconds" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadTestResult result = (LoadTestResult) o;

        if (!endPointInvocationCount.equals(result.endPointInvocationCount)) return false;
        if (!endPointName.equals(result.endPointName)) return false;
        if (!successCount.equals(result.successCount)) return false;
        if (!failureCount.equals(result.failureCount)) return false;
        return loadTestDuration.equals(result.loadTestDuration);

    }

    @Override
    public int hashCode() {
        int result = endPointInvocationCount.hashCode();
        result = 31 * result + endPointName.hashCode();
        result = 31 * result + successCount.hashCode();
        result = 31 * result + failureCount.hashCode();
        result = 31 * result + loadTestDuration.hashCode();
        return result;
    }
}
