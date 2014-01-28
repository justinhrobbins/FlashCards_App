
package org.robbins.flashcards.model;

import java.io.Serializable;

public abstract class AbstractPersistable<PK extends Serializable> {

    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(final PK id) {
        this.id = id;
    }

    public boolean isNew() {
        return null == getId();
    }
}
