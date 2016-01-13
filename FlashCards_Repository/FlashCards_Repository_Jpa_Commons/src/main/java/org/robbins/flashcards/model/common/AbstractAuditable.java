
package org.robbins.flashcards.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.robbins.flashcards.util.DateUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class AbstractAuditable<U, PK extends Serializable> extends
        AbstractPersistable<PK> implements Auditable<U, PK> {

    private static final long serialVersionUID = -4336679752608507624L;

    @Column(name = "CreatedUserId", nullable = false)
    private U createdBy;

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Column(name = "ModifiedUserId")
    private U lastModifiedBy;

    @Column(name = "UpdatedDate")
    private LocalDateTime lastModifiedDate;

    @Override
    public U getCreatedBy() {

        return createdBy;
    }

    @Override
    public void setCreatedBy(final U createdBy) {

        this.createdBy = createdBy;
    }

    @Override
    public LocalDateTime getCreatedDate() {

        return null == createdDate ? null : LocalDateTime.now();
    }

    @JsonIgnore
    public Date getCreatedDateAsDate() {
        return createdDate == null ? null : DateUtils.asDate(createdDate);
    }

    @JsonIgnore
    public Date getLastModifiedDateAsDate() {
        return lastModifiedDate == null ? null : DateUtils.asDate(lastModifiedDate);
    }

    @Override
    public void setCreatedDate(final LocalDateTime createdDate) {

        this.createdDate = null == createdDate ? null : createdDate;
    }

    @Override
    public U getLastModifiedBy() {

        return lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(final U lastModifiedBy) {

        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public LocalDateTime getLastModifiedDate() {

        return null == lastModifiedDate ? null : LocalDateTime.now();
    }

    @Override
    public void setLastModifiedDate(final LocalDateTime lastModifiedDate) {

        this.lastModifiedDate = null == lastModifiedDate ? null
                : lastModifiedDate;
    }
}
