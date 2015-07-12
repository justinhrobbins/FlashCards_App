
package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.robbins.flashcards.model.common.AbstractAuditable;

@Entity
@Table(name = "tag")
@AttributeOverride(name = "id", column = @Column(name = "TagId"))
@NamedEntityGraph(name = "Tag.flashcards",
        attributeNodes = @NamedAttributeNode("flashcards"))
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Tag tag = (Tag) o;

        if (!name.equals(tag.name)) return false;
        return !(flashcards != null ? !flashcards.equals(tag.flashcards) : tag.flashcards != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name == null ? 0 : name.hashCode());
        return result;
    }
}
