
package org.robbins.flashcards.presentation.facade.impl;

import javax.inject.Inject;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.presentation.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.service.TagService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class DefaultTagFacade extends AbstractCrudFacadeImpl<TagDto> implements
        TagFacade {

    @Inject
    private TagService service;

    @Override
    public TagService getService() {
        return service;
    }

    @Override
    public TagDto findByName(final String name) throws ServiceException {
        return service.findByName(name);
    }
}
