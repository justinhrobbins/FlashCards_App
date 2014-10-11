package org.robbins.flashcards.repository.conversion.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.repository.domain.TagCassandra;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("tagDtoConverter")
public class DefaultTagDtoConverter extends AbstractDtoConverter implements DtoConverter<TagDto, TagCassandra> {

    @Override
    public TagDto getDto(final TagCassandra entity) throws RepositoryException {
        return getMapper().map(entity, TagDto.class);
    }

    @Override
    public TagDto getDto(final TagCassandra entity, final Set<String> fields)
            throws RepositoryException
	{
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public TagCassandra getEntity(final TagDto dto) {
        return getMapper().map(dto, TagCassandra.class);
    }

    @Override
    public List<TagDto> getDtos(List<TagCassandra> entities) throws RepositoryException {
        List<TagDto> dtos = new ArrayList<>();
        for (TagCassandra entity : entities) {
            dtos.add(getDto(entity));
        }
        return dtos;
    }

    @Override
    public List<TagDto> getDtos(final List<TagCassandra> entities, final Set<String> fields)
            throws RepositoryException {throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<TagCassandra> getEtnties(final List<TagDto> dtos) {
        List<TagCassandra> entities = new ArrayList<>();
        for (TagDto dto : dtos) {
            entities.add(getEntity(dto));
        }
        return entities;
    }
}
