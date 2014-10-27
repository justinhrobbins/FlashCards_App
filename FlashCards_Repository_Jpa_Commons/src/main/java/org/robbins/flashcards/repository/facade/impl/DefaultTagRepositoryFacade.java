
package org.robbins.flashcards.repository.facade.impl;

import javax.inject.Inject;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.conversion.DtoConverter;
import org.robbins.flashcards.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("tagRepositoryFacade")
public class DefaultTagRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<TagDto, Tag> implements
        TagFacade {

    @Inject
	private TagRepository repository;

    @Inject
    @Qualifier("tagDtoConverter")
    private DtoConverter<TagDto, Tag> converter;

    @Override
    public DtoConverter<TagDto, Tag> getConverter()
    {
        return converter;
    }

    @Override
	public TagRepository getRepository() {
		return repository;
	}

    @Override
    public TagDto findByName(final String name) throws RepositoryException {
        Tag result = getRepository().findByName(name);

        if (result == null) {
            return null;
        }
        return getConverter().getDto(result);
    }
}
