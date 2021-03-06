
package org.robbins.flashcards.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;

public abstract class AbstractPersistableDto implements Persistable<Long> {

    private static final long serialVersionUID = -7383983165721894674L;

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }
}
