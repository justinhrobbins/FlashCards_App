
package org.robbins.flashcards.service;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;

import java.util.List;
import java.util.Set;

public interface TagService extends GenericPagingAndSortingService<TagDto, Long>
{

    TagDto findByName(final String name) throws FlashCardsException;
    List<TagDto> findTagsForFlashCard(final Long flashCardId, final Set<String> fields) throws FlashCardsException;
}
