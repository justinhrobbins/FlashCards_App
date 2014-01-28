
package org.robbins.flashcards.dto;

import java.util.Date;

public abstract class AbstractAuditableDto extends AbstractPersistableDto {

    private static final long serialVersionUID = -6807342050233167498L;

    private UserDto createdBy;

    private Date createdDate;

    private UserDto lastModifiedBy;

    private Date lastModifiedDate;

    public UserDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDto createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate == null ? null : (Date) createdDate.clone();
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate == null ? null : (Date) createdDate.clone();
    }

    public UserDto getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(UserDto lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate == null ? null : (Date) lastModifiedDate.clone();
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate == null ? null
                : (Date) lastModifiedDate.clone();
    }
}
