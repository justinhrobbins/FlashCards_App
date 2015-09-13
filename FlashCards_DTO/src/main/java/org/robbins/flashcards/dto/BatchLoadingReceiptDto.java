
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
    private int successCount;
    private int failureCount;
    private Date startTime;
    private Date endTime;

    public BatchLoadingReceiptDto() {
    }

    public BatchLoadingReceiptDto(final String type, final int successCount, final int failureCount, final Date startTime, final Date endTime) {
        this.type = type;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BatchLoadingReceiptDto(final String id) {
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
        return "BatchLoadingReceiptDto{" +
                "type='" + type + '\'' +
                ", successCount=" + successCount +
                ", failureCount=" + failureCount +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
