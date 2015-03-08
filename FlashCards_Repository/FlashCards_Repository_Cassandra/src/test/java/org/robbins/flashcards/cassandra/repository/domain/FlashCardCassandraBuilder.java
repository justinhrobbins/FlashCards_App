package org.robbins.flashcards.cassandra.repository.domain;

import java.util.UUID;

public class FlashCardCassandraBuilder {
    private final FlashCardCassandraEntity flashcard = new FlashCardCassandraEntity();

    public FlashCardCassandraBuilder withId(final UUID id) {
        this.flashcard.setId(id);
        return this;
    }

    public FlashCardCassandraBuilder withQuestion(final String question) {
        this.flashcard.setQuestion(question);
        return this;
    }

    public FlashCardCassandraBuilder withAnswer(final String answer) {
        this.flashcard.setQuestion(answer);
        return this;
    }

    public FlashCardCassandraBuilder withTag(final TagCassandraEntity tag) {
        this.flashcard.getTags().put(tag.getId(), tag.getName());
        return this;
    }

    public FlashCardCassandraEntity build()
    {
        return this.flashcard;
    }
}