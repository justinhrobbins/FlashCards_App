
package org.robbins.flashcards.service;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;
import org.springframework.data.domain.PageRequest;

public interface FlashCardService extends GenericPagingAndSortingService<FlashCardDto, Long>
{

    List<FlashCardDto> findByTagsIn(Set<TagDto> tags) throws FlashcardsException;
    List<FlashCardDto> findByTagsIn(Set<TagDto> tags, PageRequest page) throws FlashcardsException;
    List<FlashCardDto> findByQuestionLike(String question) throws FlashcardsException;
    List<FlashCardDto> findByQuestionLike(String question, PageRequest page) throws FlashcardsException;
	FlashCardDto findByQuestion(String question) throws FlashcardsException;
    List<FlashCardDto> findFlashcardsForTag(final Long tagId, final Set<String> fields) throws FlashcardsException;
}
