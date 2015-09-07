package org.robbins.load.tester.message;

import org.robbins.flashcards.dto.AbstractAuditableDto;

import java.io.Serializable;

public class LoadTestStart implements Serializable {

    private final Integer totalLoadCount;
    private final String endPointName;
    private final Integer batchSize;
    private final Class<? extends AbstractAuditableDto> dtoClass;

    public LoadTestStart(final String endPointName, final Integer totalLoadCount, final Integer batchSize, final Class<? extends AbstractAuditableDto> dtoClass) {
        this.totalLoadCount = totalLoadCount;
        this.endPointName = endPointName;
        this.batchSize = batchSize;
        this.dtoClass = dtoClass;
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

    public Class<? extends AbstractAuditableDto> getDtoClass() {
        return dtoClass;
    }

    @Override
    public String toString() {
        return "LoadTestStart{" +
                "totalLoadCount=" + totalLoadCount +
                ", endPointName='" + endPointName + '\'' +
                ", batchSize=" + batchSize +
                ", dtoClass=" + dtoClass +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadTestStart that = (LoadTestStart) o;

        if (!totalLoadCount.equals(that.totalLoadCount)) return false;
        if (!endPointName.equals(that.endPointName)) return false;
        if (!batchSize.equals(that.batchSize)) return false;
        return dtoClass.equals(that.dtoClass);

    }

    @Override
    public int hashCode() {
        int result = totalLoadCount.hashCode();
        result = 31 * result + endPointName.hashCode();
        result = 31 * result + batchSize.hashCode();
        result = 31 * result + dtoClass.hashCode();
        return result;
    }
}
