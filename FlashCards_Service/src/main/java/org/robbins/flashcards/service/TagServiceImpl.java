
package org.robbins.flashcards.service;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.service.base.AbstractCrudServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl extends AbstractCrudServiceImpl<TagDto, Long> implements
        TagService {

    @Inject
	@Qualifier("tagRepositoryFacade")
    private TagFacade facade;

	@Override
	public GenericCrudFacade<TagDto, Long> getFacade() {
		return facade;
	}

    @Override
    public TagDto findByName(final String name) throws FlashCardsException
    {
        return facade.findByName(name);
    }

    @Override
    public List<TagDto> findTagsForFlashCard(final Long flashCardId, Set<String> fields) throws FlashCardsException
    {
        return facade.findTagsForFlashCard(flashCardId, fields);
    }
}
