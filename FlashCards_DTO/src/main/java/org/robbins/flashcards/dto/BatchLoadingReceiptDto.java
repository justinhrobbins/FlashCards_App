
package org.robbins.flashcards.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@XmlRootElement(name = "batchloadingreceipt")
@XmlAccessorType(XmlAccessType.FIELD)
public class BatchLoadingReceiptDto extends AbstractAuditableDto implements Serializable {

    private String type;
    private int batchSize;
    private int totalSize;
    private int successCount;
    private int failureCount;
    private Date startTime;
    private Date endTime;

    public BatchLoadingReceiptDto() {
    }

    public BatchLoadingReceiptDto(final String type, final int batchSize, final int totalSize, final int successCount, final int failureCount, final Date startTime, final Date endTime) {
        this.type = type;
        this.batchSize = batchSize;
        this.totalSize = totalSize;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "BatchLoadingReceiptDto{" +
                "type='" + type + '\'' +
                ", batchSize=" + batchSize +
                ", totalSize=" + totalSize +
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

        BatchLoadingReceiptDto that = (BatchLoadingReceiptDto) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
