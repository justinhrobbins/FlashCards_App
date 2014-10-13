
package org.robbins.flashcards.presentation.facade.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.presentation.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.service.FlashCardService;
import org.robbins.flashcards.service.TagService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class DefaultFlashcardFacade extends
        AbstractCrudFacadeImpl<FlashCardDto> implements FlashcardFacade {

    @Inject
    private FlashCardService flashcardService;

    @Inject
    private TagService tagService;

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
            throws ServiceException {
        return flashcardService.findByTagsIn(tagDtos);
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tagDtos,
            final PageRequest page) throws ServiceException {
        return flashcardService.findByTagsIn(tagDtos);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question)
            throws ServiceException {
        return flashcardService.findByQuestionLike(question);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question,
            final PageRequest page)
            throws ServiceException {
        return flashcardService.findByQuestionLike(question, page);
    }

    @Override
    public FlashCardDto findByQuestion(final String question) throws ServiceException {
        return flashcardService.findByQuestion(question);
    }
}
