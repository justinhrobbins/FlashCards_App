
package org.robbins.flashcards.service;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.FlashCardFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.service.base.AbstractCrudServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
public class FlashCardServiceImpl extends AbstractCrudServiceImpl<FlashCardDto, Long>
        implements FlashCardService {

    @Inject
	@Qualifier("flashcardRepositoryFacade")
    private FlashCardFacade facade;

	@Override
	public GenericCrudFacade<FlashCardDto, Long> getFacade() {
		return facade;
	}

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tags) throws FlashCardsException
	{
        return facade.findByTagsIn(tags);
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tags, final PageRequest page) throws FlashCardsException
    {
        return facade.findByTagsIn(tags, page);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question) throws FlashCardsException
    {
        return facade.findByQuestionLike(question);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question,
            final PageRequest page) throws FlashCardsException
    {
        return facade.findByQuestionLike(question, page);
    }

    @Override
    public FlashCardDto findByQuestion(final String question) throws FlashCardsException
    {
        return facade.findByQuestion(question);
    }

    @Override
    public List<FlashCardDto> findFlashCardsForTag(final Long tagId, final Set<String> fields) throws FlashCardsException
    {
        return facade.findFlashCardsForTag(tagId, fields);
    }
}
