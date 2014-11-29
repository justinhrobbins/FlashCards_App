
package org.robbins.flashcards.facade;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;

import java.util.List;
import java.util.Set;

public interface TagFacade extends GenericCrudFacade<TagDto> {

    TagDto findByName(final String name) throws FlashcardsException;
    List<TagDto> findTagsForFlashcard(final Long flashcardId, final Set<String> fields) throws FlashcardsException;
}
