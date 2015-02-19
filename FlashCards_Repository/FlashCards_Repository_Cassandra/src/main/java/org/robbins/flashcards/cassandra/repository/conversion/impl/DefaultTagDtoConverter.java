package org.robbins.flashcards.cassandra.repository.conversion.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraDto;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.repository.conversion.impl.AbstractDtoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("tagDtoConverter")
public class DefaultTagDtoConverter extends AbstractDtoConverter implements DtoConverter<TagDto, TagCassandraDto> {

    @Override
    public TagDto getDto(final TagCassandraDto entity) throws RepositoryException {
        return getMapper().map(entity, TagDto.class);
    }

    @Override
    public TagDto getDto(final TagCassandraDto entity, final Set<String> fields)
            throws RepositoryException
	{
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public TagCassandraDto getEntity(final TagDto dto) {
        return getMapper().map(dto, TagCassandraDto.class);
    }

    @Override
    public List<TagDto> getDtos(List<TagCassandraDto> entities) throws RepositoryException {
        List<TagDto> dtos = new ArrayList<>();
        for (TagCassandraDto entity : entities) {
            dtos.add(getDto(entity));
        }
        return dtos;
    }

    @Override
    public List<TagDto> getDtos(final List<TagCassandraDto> entities, final Set<String> fields)
            throws RepositoryException {throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<TagCassandraDto> getEntities(final List<TagDto> dtos) {
        List<TagCassandraDto> entities = new ArrayList<>();
        for (TagDto dto : dtos) {
            entities.add(getEntity(dto));
        }
        return entities;
    }
}
