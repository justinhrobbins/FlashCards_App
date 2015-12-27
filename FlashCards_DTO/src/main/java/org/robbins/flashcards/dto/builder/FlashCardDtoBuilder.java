package org.robbins.flashcards.dto.builder;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;

public class FlashCardDtoBuilder {
    private FlashCardDto flashcard = new FlashCardDto();

    public FlashCardDtoBuilder() {}

    public FlashCardDtoBuilder withId(final Long id) {
        flashcard.setId(id);
        return this;
    }

    public FlashCardDtoBuilder withQuestion(final String question) {
        flashcard.setQuestion(question);
        return this;
    }

    public FlashCardDtoBuilder withAnswer(final String answer) {
        flashcard.setAnswer(answer);
        return this;
    }

    public FlashCardDtoBuilder withTag(final TagDto tag) {
        flashcard.getTags().add(tag);
        return this;
    }

    public FlashCardDto build() {
        return flashcard;
    }
}
