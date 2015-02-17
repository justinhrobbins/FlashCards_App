package org.robbins.flashcards.dto;

/**
 * Created by justinrobbins on 2/16/15.
 */
public class FlashCardDtoBuilder {
    private FlashCardDto flashcard = new FlashCardDto();

    public FlashCardDtoBuilder() {}

    public FlashCardDtoBuilder withId(final String id) {
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

    public FlashCardDto build() {
        return flashcard;
    }
}
