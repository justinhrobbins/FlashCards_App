
package org.robbins.flashcards.facade;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;

public interface FlashcardFacade extends GenericCrudFacade<FlashCardDto> {

    List<FlashCardDto> findByTagsIn(Set<TagDto> tags) throws ServiceException;

    List<FlashCardDto> findByTagsIn(Set<TagDto> tags, PageRequest page)
            throws ServiceException;

    List<FlashCardDto> findByQuestionLike(String question) throws ServiceException;

    List<FlashCardDto> findByQuestionLike(String question, PageRequest page)
            throws ServiceException;

    FlashCardDto findByQuestion(String question) throws ServiceException;
}
