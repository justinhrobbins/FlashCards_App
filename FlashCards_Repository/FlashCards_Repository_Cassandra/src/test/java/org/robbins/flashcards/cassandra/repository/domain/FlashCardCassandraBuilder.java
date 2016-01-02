package org.robbins.flashcards.cassandra.repository.domain;

public class FlashCardCassandraBuilder {
    private final FlashCardCassandraEntity flashCard = new FlashCardCassandraEntity();

    public FlashCardCassandraBuilder withId(final Long id) {
        this.flashCard.setId(id);
        return this;
    }

    public FlashCardCassandraBuilder withQuestion(final String question) {
        this.flashCard.setQuestion(question);
        return this;
    }

    public FlashCardCassandraBuilder withAnswer(final String answer) {
        this.flashCard.setQuestion(answer);
        return this;
    }

    public FlashCardCassandraBuilder withTag(final TagCassandraEntity tag) {
        this.flashCard.getTags().put(tag.getId(), tag.getName());
        return this;
    }

    public FlashCardCassandraEntity build()
    {
        return this.flashCard;
    }
}