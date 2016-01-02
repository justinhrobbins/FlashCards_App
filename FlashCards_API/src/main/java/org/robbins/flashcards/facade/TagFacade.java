
package org.robbins.flashcards.facade;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;

import java.util.List;
import java.util.Set;

public interface TagFacade extends GenericCrudFacade<TagDto, Long> {

    TagDto findByName(final String name) throws FlashCardsException;
    List<TagDto> findTagsForFlashCard(final Long flashCardId, final Set<String> fields) throws FlashCardsException;
}
