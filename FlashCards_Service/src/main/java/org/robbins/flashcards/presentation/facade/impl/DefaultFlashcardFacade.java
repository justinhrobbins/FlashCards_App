
package org.robbins.flashcards.presentation.facade.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.presentation.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.service.FlashCardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("presentationFlashcardFacade")
public class DefaultFlashcardFacade extends
        AbstractCrudFacadeImpl<FlashCardDto, String> implements FlashcardFacade {

    @Inject
    private FlashCardService flashcardService;

    @Override
    public FlashCardService getService() {
        return flashcardService;
    }

    @Override
    public FlashCardDto save(final FlashCardDto dto) throws FlashcardsException {
        return getService().save(dto);
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tagDtos)
            throws FlashcardsException {
        return flashcardService.findByTagsIn(tagDtos);
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tagDtos,
            final PageRequest page) throws FlashcardsException {
        return flashcardService.findByTagsIn(tagDtos);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question)
            throws FlashcardsException {
        return flashcardService.findByQuestionLike(question);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question,
            final PageRequest page)
            throws FlashcardsException {
        return flashcardService.findByQuestionLike(question, page);
    }

    @Override
    public FlashCardDto findByQuestion(final String question) throws FlashcardsException {
        return flashcardService.findByQuestion(question);
    }

    @Override
    public List<FlashCardDto> findFlashcardsForTag(final String tagId, Set<String> fields) throws FlashcardsException {
        return getService().findFlashcardsForTag(tagId, fields);
    }
}
