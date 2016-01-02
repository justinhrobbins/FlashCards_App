
package org.robbins.flashcards.cassandra.repository.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "batchreceipt")
public class BatchLoadingReceiptCassandraEntity extends AbstractPersistable implements Serializable {

    @Column
    private String type;

    @Column
    private int successCount;

    @Column
    private int failureCount;

    @Column
    private Date startTime;

    @Column
    private Date endTime;

    public BatchLoadingReceiptCassandraEntity() {
    }

    public BatchLoadingReceiptCassandraEntity(final String type, final int successCount,
            final int failureCount, final Date startTime,
            final Date endTime) {
        this.type = type;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BatchLoadingReceiptCassandraEntity(final Long id) {
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
}
