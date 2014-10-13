
package org.robbins.flashcards.service;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;
import org.springframework.data.domain.PageRequest;

public interface FlashCardService extends GenericPagingAndSortingService<FlashCardDto, Long>
{

    List<FlashCardDto> findByTagsIn(Set<TagDto> tags);

    List<FlashCardDto> findByTagsIn(Set<TagDto> tags, PageRequest page);

    List<FlashCardDto> findByQuestionLike(String question);

    List<FlashCardDto> findByQuestionLike(String question, PageRequest page);

	FlashCardDto findByQuestion(String question);
}
