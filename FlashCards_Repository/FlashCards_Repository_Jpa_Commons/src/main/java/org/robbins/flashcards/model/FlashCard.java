
package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.robbins.flashcards.model.common.AbstractAuditable;

@Entity
@Table(name = "flashcard")
@AttributeOverride(name = "id", column = @Column(name = "FlashCardId"))
@NamedEntityGraph(name = "FlashCard.tags",
        attributeNodes = @NamedAttributeNode("tags"))
public class FlashCard extends AbstractAuditable<Long, Long> implements Serializable {

    private static final long serialVersionUID = -3461056579037652853L;

    @Column(name = "Question", unique = true, nullable = false)
    private String question;

    @Column(name = "Answer", nullable = false, columnDefinition = "LONGTEXT")
    private String answer;

    @ManyToMany(targetEntity = org.robbins.flashcards.model.Tag.class, cascade = {
        CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "flashcard_tag", joinColumns = @JoinColumn(name = "FlashCardId"), inverseJoinColumns = @JoinColumn(name = "TagId"))
    @OrderBy("name")
    private Set<Tag> tags = new HashSet<>(0);

    @ElementCollection
    @CollectionTable(name = "flashcard_link", joinColumns = @JoinColumn(name = "FlashCardId"))
    @Column(name = "Link", nullable = false)
    @OrderColumn(name = "link_idx")
    private List<String> links = new ArrayList<String>(0);

    public FlashCard() {
    }

    public FlashCard(final Long flashCardId) {
        setId(flashCardId);
    }

    public FlashCard(final String question, final String answer) {
        this.question = question;
        this.answer = answer;
    }

    public FlashCard(final String question, final String answer, final Set<Tag> tags,
            final List<String> links) {
        this.question = question;
        this.answer = answer;
        this.tags = tags;
        this.links = links;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(final Set<Tag> tags) {
        this.tags = tags;
    }

    public List<String> getLinks() {
        return this.links;
    }

    public void setLinks(final List<String> links) {
        this.links = links;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof FlashCard))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }

        final FlashCard flashCard = (FlashCard) o;

        if (!answer.equals(flashCard.answer))
        {
            return false;
        }
        if (!question.equals(flashCard.question))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + question.hashCode();
        result = 31 * result + answer.hashCode();
        return result;
    }
}
