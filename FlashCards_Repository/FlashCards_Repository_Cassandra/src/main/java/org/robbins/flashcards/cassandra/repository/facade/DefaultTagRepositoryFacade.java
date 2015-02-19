
package org.robbins.flashcards.cassandra.repository.facade;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.TagRepository;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraDto;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.TagFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Component("tagRepositoryFacade")
public class DefaultTagRepositoryFacade implements
        TagFacade {

    @Inject
	private TagRepository repository;

    @Inject
    @Qualifier("tagDtoConverter")
    private DtoConverter<TagDto, TagCassandraDto> converter;

    public DtoConverter<TagDto, TagCassandraDto> getConverter()
    {
        return converter;
    }

	public TagRepository getRepository() {
		return repository;
	}

    @Override
    public TagDto findByName(final String name) throws RepositoryException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<TagDto> findTagsForFlashcard(final String flashcardId, final Set<String> fields) throws RepositoryException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<TagDto> list() throws FlashcardsException {
        return list(null, null, null, null);
    }

    @Override
    public List<TagDto> list(final Integer page, final Integer size, final String sort, final String direction) throws FlashcardsException {
        return list(null, null, null, null, null);
    }

    @Override
    public List<TagDto> list(final Integer page, final Integer size, final String sort, final String direction, final Set<String> fields) throws FlashcardsException {
        List<TagCassandraDto> tags = Lists.newArrayList(getRepository().findAll());
        return getConverter().getDtos(tags);
    }

    @Override
    public Long count() {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public TagDto findOne(final String id) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public TagDto findOne(final String id, final Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public TagDto save(final TagDto entity) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public void delete(final String id) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<TagDto> findByCreatedBy(final String userId, final Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }
}
