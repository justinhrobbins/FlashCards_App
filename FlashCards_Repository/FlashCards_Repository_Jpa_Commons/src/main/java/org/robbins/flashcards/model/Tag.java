
package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.robbins.flashcards.model.common.AbstractAuditable;

@Entity
@Table(name = "tag")
@AttributeOverride(name = "id", column = @Column(name = "TagId"))
@NamedEntityGraph(name = "Tag.flashCards",
        attributeNodes = @NamedAttributeNode("flashCards"))
public class Tag extends AbstractAuditable<Long, Long> implements Serializable {

    private static final long serialVersionUID = 3642775570292807703L;

    @Column(name = "TagName", unique = true, nullable = false)
    private String name;

    @ManyToMany(targetEntity = org.robbins.flashcards.model.FlashCard.class, cascade = {
        CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "tags", fetch = FetchType.LAZY)
    @OrderBy("question")
    private Set<FlashCard> flashCards = new HashSet<>(0);

    public Tag() {
    }

    public Tag(final Long id) {
        setId(id);
    }

    public Tag(final String name, final Set<FlashCard> flashCards) {
        this.name = name;
        this.flashCards = flashCards;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<FlashCard> getFlashCards() {
        return this.flashCards;
    }

    public void setFlashCards(final Set<FlashCard> flashCards) {
        this.flashCards = flashCards;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Tag))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }

        final Tag tag = (Tag) o;

        if (!name.equals(tag.name))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
