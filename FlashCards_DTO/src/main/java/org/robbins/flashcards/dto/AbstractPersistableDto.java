
package org.robbins.flashcards.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;

public abstract class AbstractPersistableDto implements Persistable<String> {

    private static final long serialVersionUID = -7383983165721894674L;

    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }
}
