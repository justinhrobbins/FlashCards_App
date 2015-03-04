package org.robbins.flashcards.cassandra.repository.domain;

import java.util.UUID;

public class FlashCardCassandraBuilder {
    private final FlashCardCassandraDto flashcard = new FlashCardCassandraDto();

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

    public FlashCardCassandraDto build()
    {
        return this.flashcard;
    }
}