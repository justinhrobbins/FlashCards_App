
package org.robbins.flashcards.facade;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;

public interface FlashCardFacade extends GenericCrudFacade<FlashCardDto, Long>
{
    List<FlashCardDto> findByTagsIn(final Set<TagDto> tags) throws FlashCardsException;
    List<FlashCardDto> findByTagsIn(final Set<TagDto> tags, final PageRequest page)
            throws FlashCardsException;
    List<FlashCardDto> findByQuestionLike(final String question) throws FlashCardsException;
    List<FlashCardDto> findByQuestionLike(final String question, final PageRequest page)
            throws FlashCardsException;
    FlashCardDto findByQuestion(final String question) throws FlashCardsException;
    List<FlashCardDto> findFlashCardsForTag(final Long tagId, final Set<String> fields) throws FlashCardsException;
}
