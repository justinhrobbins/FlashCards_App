package org.robbins.flashcards.client;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.facade.FlashCardFacade;

public interface FlashCardClient extends FlashCardFacade, GenericRestCrudFacade<FlashCardDto, Long> {
}
