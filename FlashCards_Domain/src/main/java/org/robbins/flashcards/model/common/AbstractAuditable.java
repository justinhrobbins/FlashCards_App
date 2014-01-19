
package org.robbins.flashcards.model.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class AbstractAuditable<U, PK extends Serializable> extends
        AbstractPersistable<PK> implements Auditable<U, PK> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4336679752608507624L;

    @ManyToOne
    @JoinColumn(name = "CreatedUserId", nullable = false)
    private U createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedDate")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "ModifiedUserId")
    private U lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedDate")
    private Date lastModifiedDate;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Auditable#getCreatedBy()
     */
    @Override
    public U getCreatedBy() {

        return createdBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Auditable#setCreatedBy(java.lang.Object)
     */
    @Override
    public void setCreatedBy(final U createdBy) {

        this.createdBy = createdBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Auditable#getCreatedDate()
     */
    @Override
    public DateTime getCreatedDate() {

        return null == createdDate ? null : new DateTime(createdDate);
    }

    @JsonIgnore
    public Date getCreatedDateAsDate() {
        return createdDate == null ? null : (Date) createdDate.clone();
    }

    @JsonIgnore
    public Date getLastModifiedDateAsDate() {
        return lastModifiedDate == null ? null : (Date) lastModifiedDate.clone();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Auditable#setCreatedDate(org.joda.time
     * .DateTime)
     */
    @Override
    public void setCreatedDate(final DateTime createdDate) {

        this.createdDate = null == createdDate ? null : createdDate.toDate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Auditable#getLastModifiedBy()
     */
    @Override
    public U getLastModifiedBy() {

        return lastModifiedBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Auditable#setLastModifiedBy(java.lang .Object)
     */
    @Override
    public void setLastModifiedBy(final U lastModifiedBy) {

        this.lastModifiedBy = lastModifiedBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Auditable#getLastModifiedDate()
     */
    @Override
    public DateTime getLastModifiedDate() {

        return null == lastModifiedDate ? null : new DateTime(lastModifiedDate);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Auditable#setLastModifiedDate(org.joda
     * .time.DateTime)
     */
    @Override
    public void setLastModifiedDate(final DateTime lastModifiedDate) {

        this.lastModifiedDate = null == lastModifiedDate ? null
                : lastModifiedDate.toDate();
    }
}
