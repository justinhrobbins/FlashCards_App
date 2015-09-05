package org.robbins.load.tester.message;

import java.io.Serializable;

public class LoadTestStart implements Serializable {

    private final Integer totalLoadCount;
    private final String endPointName;
    private final Integer batchSize;

    public LoadTestStart(Integer totalLoadCount, Integer batchSize, String endPointName) {
        this.totalLoadCount = totalLoadCount;
        this.endPointName = endPointName;
        this.batchSize = batchSize;
    }

    public Integer getTotalLoadCount() {
        return totalLoadCount;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    @Override
    public String toString() {
        return "LoadTestStart{" +
                "totalLoadCount=" + totalLoadCount +
                ", endPointName='" + endPointName + '\'' +
                ", batchSize=" + batchSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadTestStart that = (LoadTestStart) o;

        if (!totalLoadCount.equals(that.totalLoadCount)) return false;
        if (!endPointName.equals(that.endPointName)) return false;
        return batchSize.equals(that.batchSize);

    }

    @Override
    public int hashCode() {
        int result = totalLoadCount.hashCode();
        result = 31 * result + endPointName.hashCode();
        result = 31 * result + batchSize.hashCode();
        return result;
    }
}
