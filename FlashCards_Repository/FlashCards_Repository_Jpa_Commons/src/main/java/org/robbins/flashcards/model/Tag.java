
package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.robbins.flashcards.model.common.AbstractAuditable;

@Entity
@Table(name = "tag")
@AttributeOverride(name = "id", column = @Column(name = "TagId"))
public class Tag extends AbstractAuditable<User, String> implements Serializable {

    private static final long serialVersionUID = 3642775570292807703L;

    @Column(name = "TagName", unique = true, nullable = false)
    private String name;

    @ManyToMany(targetEntity = org.robbins.flashcards.model.FlashCard.class, cascade = {
        CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "tags", fetch = FetchType.LAZY)
    @OrderBy("question")
    private Set<FlashCard> flashcards = new HashSet<FlashCard>(0);

    public Tag() {
    }

    public Tag(final String id) {
        setId(id);
    }

    public Tag(final String name, final Set<FlashCard> flashcards) {
        this.name = name;
        this.flashcards = flashcards;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<FlashCard> getFlashcards() {
        return this.flashcards;
    }

    public void setFlashcards(final Set<FlashCard> flashcards) {
        this.flashcards = flashcards;
    }

    /**
     * toString
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(
                Integer.toHexString(hashCode())).append(" [");
        buffer.append("id").append("='").append(getId()).append("' ");
        buffer.append("name").append("='").append(getName()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }
}
