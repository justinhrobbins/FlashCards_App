
package org.robbins.flashcards.facade.impl;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Component
public class DefaultTagFacade extends AbstractCrudFacadeImpl<TagDto, Tag> implements
        TagFacade {

    @Inject
    private TagService service;

    @Inject
    @Qualifier("tagDtoConverter")
    private DtoConverter<TagDto, Tag> converter;

    @Override
    public DtoConverter<TagDto, Tag> getConverter()
    {
        return converter;
    }

    @Override
    public TagService getService() {
        return service;
    }

    @Override
    public TagDto findByName(final String name) throws ServiceException {
        Tag result = service.findByName(name);

        if (result == null) {
            return null;
        }
        return getConverter().getDto(result);
    }
}
