package org.robbins.flashcards.dto.builder;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;

public class FlashCardDtoBuilder {
    private FlashCardDto flashCardId = new FlashCardDto();

    public FlashCardDtoBuilder() {}

    public FlashCardDtoBuilder withId(final Long id) {
        flashCardId.setId(id);
        return this;
    }

    public FlashCardDtoBuilder withQuestion(final String question) {
        flashCardId.setQuestion(question);
        return this;
    }

    public FlashCardDtoBuilder withAnswer(final String answer) {
        flashCardId.setAnswer(answer);
        return this;
    }

    public FlashCardDtoBuilder withTag(final TagDto tag) {
        flashCardId.getTags().add(tag);
        return this;
    }

    public FlashCardDto build() {
        return flashCardId;
    }
}
