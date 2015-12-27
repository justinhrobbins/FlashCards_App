package org.robbins.flashcards.client;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.facade.TagFacade;

public interface TagClient extends TagFacade, GenericRestCrudFacade<TagDto, Long> {
}
