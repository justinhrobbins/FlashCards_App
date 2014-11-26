
package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public abstract class AbstractAuditable<U, PK extends Serializable> extends
        AbstractPersistable<PK> {

    private U createdBy;

    private Date createdDate;

    private U lastModifiedBy;

    private Date lastModifiedDate;

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final U createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @JsonIgnore
    public Date getCreatedDateAsDate() {
        return createdDate;
    }

    @JsonIgnore
    public Date getLastModifiedDateAsDate() {
        return lastModifiedDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(final U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
