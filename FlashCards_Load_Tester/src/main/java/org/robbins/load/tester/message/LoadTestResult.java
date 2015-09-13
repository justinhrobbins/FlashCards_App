package org.robbins.load.tester.message;

import java.io.Serializable;

public class LoadTestResult implements Serializable {
    private final Integer totalLoadCount;
    private final String endPointName;
    private final Integer successCount;
    private final Integer failureCount;
    private final Long loadTestDuration;

    public LoadTestResult(final Integer totalLoadCount, final String endPointName,
                          final Integer successCount, final Integer failureCount, final Long loadTestDuration) {
        this.totalLoadCount = totalLoadCount;
        this.endPointName = endPointName;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.loadTestDuration = loadTestDuration;
    }

    public Integer getGetTotalLoadCount() {
        return totalLoadCount;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public Long getLoadTestDuration() {
        return loadTestDuration;
    }

    @Override
    public String toString() {
        return "LoadTestResult{" +
                "totalLoadCount=" + totalLoadCount +
                ", endPointName='" + endPointName + '\'' +
                ", successCount=" + successCount +
                ", failureCount=" + failureCount +
                ", loadTestDuration=" + loadTestDuration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadTestResult result = (LoadTestResult) o;

        if (!totalLoadCount.equals(result.totalLoadCount)) return false;
        if (!endPointName.equals(result.endPointName)) return false;
        if (!successCount.equals(result.successCount)) return false;
        if (!failureCount.equals(result.failureCount)) return false;
        return loadTestDuration.equals(result.loadTestDuration);

    }

    @Override
    public int hashCode() {
        int result = totalLoadCount.hashCode();
        result = 31 * result + endPointName.hashCode();
        result = 31 * result + successCount.hashCode();
        result = 31 * result + failureCount.hashCode();
        result = 31 * result + loadTestDuration.hashCode();
        return result;
    }
}
