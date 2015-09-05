package org.robbins.load.tester.message;

import org.robbins.flashcards.dto.TagDto;

import java.io.Serializable;
import java.util.List;

public class BatchTestStart implements Serializable {
    private final String endPointName;
    private final List<TagDto> batch;

    public BatchTestStart(final String endPointName, final List<TagDto> batch) {
        this.endPointName = endPointName;
        this.batch = batch;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public List<TagDto> getBatch() {
        return batch;
    }

    @Override
    public String toString() {
        return "BatchTestStart{" +
                "endPointName='" + endPointName + '\'' +
                ", batch=" + batch +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatchTestStart that = (BatchTestStart) o;

        if (!endPointName.equals(that.endPointName)) return false;
        return batch.equals(that.batch);

    }

    @Override
    public int hashCode() {
        int result = endPointName.hashCode();
        result = 31 * result + batch.hashCode();
        return result;
    }
}