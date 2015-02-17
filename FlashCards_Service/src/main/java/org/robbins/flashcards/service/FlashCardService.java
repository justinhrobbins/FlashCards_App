
package org.robbins.flashcards.service;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;

public interface FlashCardService extends GenericPagingAndSortingService<FlashCardDto, String>
{

    List<FlashCardDto> findByTagsIn(final Set<TagDto> tags) throws FlashcardsException;
    List<FlashCardDto> findByTagsIn(final Set<TagDto> tags, final PageRequest page) throws FlashcardsException;
    List<FlashCardDto> findByQuestionLike(final String question) throws FlashcardsException;
    List<FlashCardDto> findByQuestionLike(final String question, PageRequest page) throws FlashcardsException;
	FlashCardDto findByQuestion(final String question) throws FlashcardsException;
    List<FlashCardDto> findFlashcardsForTag(final String tagId, final Set<String> fields) throws FlashcardsException;
}
