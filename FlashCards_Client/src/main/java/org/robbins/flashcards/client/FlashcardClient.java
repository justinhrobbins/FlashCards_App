package org.robbins.flashcards.client;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.facade.FlashcardFacade;

public interface FlashcardClient extends FlashcardFacade, GenericRestCrudFacade<FlashCardDto> {
}
