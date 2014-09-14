package org.robbins.flashcards.client;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.facade.TagFacade;

public interface FlashcardClient extends FlashcardFacade, GenericRestCrudFacade<FlashCardDto> {
}
