package org.robbins.flashcards.cassandra.repository.conversion.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.repository.conversion.impl.AbstractDtoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("tagDtoConverter")
public class DefaultTagDtoConverter extends AbstractDtoConverter implements DtoConverter<TagDto, TagCassandraEntity> {

    @Override
    public TagDto getDto(final TagCassandraEntity entity) throws RepositoryException {
        return getMapper().map(entity, TagDto.class);
    }

    @Override
    public TagDto getDto(final TagCassandraEntity entity, final Set<String> fields)
            throws RepositoryException
	{
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public TagCassandraEntity getEntity(final TagDto dto) {
        return getMapper().map(dto, TagCassandraEntity.class);
    }

    @Override
    public List<TagDto> getDtos(List<TagCassandraEntity> entities) throws RepositoryException {
        List<TagDto> dtos = new ArrayList<>();
        for (TagCassandraEntity entity : entities) {
            dtos.add(getDto(entity));
        }
        return dtos;
    }

    @Override
    public List<TagDto> getDtos(final List<TagCassandraEntity> entities, final Set<String> fields)
            throws RepositoryException {throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<TagCassandraEntity> getEntities(final List<TagDto> dtos) {
        List<TagCassandraEntity> entities = new ArrayList<>();
        for (TagDto dto : dtos) {
            entities.add(getEntity(dto));
        }
        return entities;
    }
}
