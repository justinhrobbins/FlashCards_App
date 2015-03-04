
package org.robbins.flashcards.cassandra.repository.facade;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraDto;
import org.robbins.flashcards.cassandra.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.repository.TagRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component("tagRepositoryFacade")
public class DefaultTagRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<TagDto, TagCassandraDto> implements
        TagFacade {

    @Inject
	private TagRepository<TagCassandraDto, UUID> repository;

    @Inject
    @Qualifier("tagDtoConverter")
    private DtoConverter<TagDto, TagCassandraDto> converter;

    public DtoConverter<TagDto, TagCassandraDto> getConverter()
    {
        return converter;
    }

	public TagRepository<TagCassandraDto, UUID> getRepository() {
		return repository;
	}

    @Override
    public TagDto findByName(final String name) throws RepositoryException {
        final TagCassandraDto tag = repository.findByName(name);
        return tag == null ? null : getConverter().getDto(tag);
    }

    @Override
    public List<TagDto> findTagsForFlashcard(final String flashcardId, final Set<String> fields) throws RepositoryException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<TagDto> findByCreatedBy(final String userId, final Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }
}
