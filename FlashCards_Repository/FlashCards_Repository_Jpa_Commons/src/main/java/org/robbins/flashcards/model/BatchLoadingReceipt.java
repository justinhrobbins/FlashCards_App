
package org.robbins.flashcards.model;

import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.model.common.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "batchloadingreceipt")
@AttributeOverride(name = "id", column = @Column(name = "BatchLoadingId"))
public class BatchLoadingReceipt extends AbstractAuditable<String, String> implements Serializable {

    @Column(name = "Type", nullable = false)
    private String type;

    @Column(name = "SuccessCount", nullable = false)
    private int successCount;

    @Column(name = "FailureCount", nullable = false)
    private int failureCount;

    @Column(name = "StartTime", nullable = false)
    private Date startTime;

    @Column(name = "EndTime", nullable = true)
    private Date endTime;

    public BatchLoadingReceipt() {
    }

    public BatchLoadingReceipt(final String type, final int successCount,
                               final int failureCount, final Date startTime,
                               final Date endTime) {
        this.type = type;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BatchLoadingReceipt(final String id) {
        setId(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "BatchLoadingReceipt{" +
                "type='" + type + '\'' +
                ", successCount=" + successCount +
                ", failureCount=" + failureCount +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatchLoadingReceipt that = (BatchLoadingReceipt) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}
