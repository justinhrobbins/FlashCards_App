
package org.robbins.flashcards.presentation.facade.impl;

import javax.inject.Inject;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.presentation.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.service.TagService;
import org.springframework.stereotype.Component;

@Component("presentationTagFacade")
public class DefaultTagFacade extends AbstractCrudFacadeImpl<TagDto> implements
        TagFacade {

    @Inject
    private TagService service;

    @Override
    public TagService getService() {
        return service;
    }

    @Override
    public TagDto findByName(final String name) throws FlashcardsException
	{
        return service.findByName(name);
    }
}
