
package org.robbins.flashcards.repository.facade.impl;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Transactional
@Component("tagRepositoryFacade")
public class DefaultTagRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<TagDto, Tag, Long> implements
        TagFacade {

    @Inject
	private TagRepository<Tag, Long> repository;

    @Inject
    @Qualifier("tagDtoConverter")
    private DtoConverter<TagDto, Tag> converter;

    @Override
    public DtoConverter<TagDto, Tag> getConverter()
    {
        return converter;
    }

    @Override
	public TagRepository<Tag, Long> getRepository() {
		return repository;
	}

    @Override
    public TagDto findByName(final String name) throws RepositoryException {
        Tag result = repository.findByName(name);

        if (result == null) {
            return null;
        }
        return convertAndInitializeEntity(result);
    }

    @Override
    public List<TagDto> findTagsForFlashcard(final Long flashcardId, final Set<String> fields) throws RepositoryException {
        List<Tag> results = repository.findByFlashcards_Id(flashcardId);
        return convertAndInitializeEntities(results, fields);
    }
}
