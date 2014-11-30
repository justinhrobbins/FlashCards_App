
package org.robbins.flashcards.service;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;


import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.service.base.AbstractCrudServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FlashCardServiceImpl extends AbstractCrudServiceImpl<FlashCardDto>
        implements FlashCardService {

    @Inject
	@Qualifier("flashcardRepositoryFacade")
    private FlashcardFacade facade;

	@Override
	public GenericCrudFacade<FlashCardDto> getFacade() {
		return facade;
	}

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tags) throws FlashcardsException
	{
        return facade.findByTagsIn(tags);
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tags, final PageRequest page) throws FlashcardsException {
        return facade.findByTagsIn(tags, page);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question) throws FlashcardsException {
        return facade.findByQuestionLike(question);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question,
            final PageRequest page) throws FlashcardsException {
        return facade.findByQuestionLike(question, page);
    }

    @Override
    public FlashCardDto findByQuestion(final String question) throws FlashcardsException {
        return facade.findByQuestion(question);
    }

    @Override
    public List<FlashCardDto> findFlashcardsForTag(Long tagId, Set<String> fields) throws FlashcardsException {
        return facade.findFlashcardsForTag(tagId, fields);
    }
}
