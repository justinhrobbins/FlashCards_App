
package org.robbins.flashcards.service;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;

import java.util.List;
import java.util.Set;

public interface TagService extends GenericPagingAndSortingService<TagDto, Long>
{

    TagDto findByName(final String name) throws FlashcardsException;
    List<TagDto> findTagsForFlashcard(final Long flashcardId, final Set<String> fields) throws FlashcardsException;
}
