
package org.robbins.flashcards.service;

import javax.inject.Inject;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.service.base.AbstractCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends AbstractCrudServiceImpl<TagDto> implements
        TagService {

    @Inject
    private TagFacade facade;

	@Override
	public GenericCrudFacade<TagDto> getFacade() {
		return facade;
	}

    @Override
    public TagDto findByName(final String name) throws FlashcardsException {
        return facade.findByName(name);
    }
}
