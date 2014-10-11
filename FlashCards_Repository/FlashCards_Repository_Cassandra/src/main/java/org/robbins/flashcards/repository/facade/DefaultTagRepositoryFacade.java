
package org.robbins.flashcards.repository.facade;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.domain.TagCassandra;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Transactional
@Component("tagRepositoryFacade")
public class DefaultTagRepositoryFacade implements
        TagFacade {

    @Inject
	private TagRepository repository;

    @Inject
    @Qualifier("tagDtoConverter")
    private DtoConverter<TagDto, TagCassandra> converter;

    public DtoConverter<TagDto, TagCassandra> getConverter()
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
    public List<TagDto> findTagsForFlashcard(final Long flashcardId, final Set<String> fields) throws RepositoryException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<TagDto> list() throws FlashcardsException {
        List<TagCassandra> tags = Lists.newArrayList(getRepository().findAll());
        return getConverter().getDtos(tags);
    }

    @Override
    public List<TagDto> list(Integer page, Integer size, String sort, String direction) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<TagDto> list(Integer page, Integer size, String sort, String direction, Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public Long count() {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public TagDto findOne(Long id) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public TagDto findOne(Long id, Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public TagDto save(TagDto entity) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public void delete(Long id) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<TagDto> findByCreatedBy(Long userId, Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }
}
