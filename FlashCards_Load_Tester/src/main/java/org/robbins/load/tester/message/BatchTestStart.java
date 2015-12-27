package org.robbins.load.tester.message;

import org.robbins.flashcards.dto.AbstractAuditableDto;

import java.io.Serializable;
import java.util.List;

public class BatchTestStart implements Serializable {
    private final String endPointName;
    private final List<AbstractAuditableDto> batch;
    private final Class<? extends AbstractAuditableDto> dtoClass;

    public BatchTestStart(final String endPointName, final List<AbstractAuditableDto> batch, final Class<? extends AbstractAuditableDto> dtoClass) {
        this.endPointName = endPointName;
        this.batch = batch;
        this.dtoClass = dtoClass;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public List<AbstractAuditableDto> getBatch() {
        return batch;
    }

    public Class<? extends AbstractAuditableDto> getDtoClass() {
        return dtoClass;
    }

    @Override
    public String toString() {
        return "BatchTestStart{" +
                "endPointName='" + endPointName + '\'' +
                ", batch=" + batch +
                ", dtoClass=" + dtoClass +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatchTestStart that = (BatchTestStart) o;

        if (!endPointName.equals(that.endPointName)) return false;
        if (!batch.equals(that.batch)) return false;
        return dtoClass.equals(that.dtoClass);

    }

    @Override
    public int hashCode() {
        int result = endPointName.hashCode();
        result = 31 * result + batch.hashCode();
        result = 31 * result + dtoClass.hashCode();
        return result;
    }
}