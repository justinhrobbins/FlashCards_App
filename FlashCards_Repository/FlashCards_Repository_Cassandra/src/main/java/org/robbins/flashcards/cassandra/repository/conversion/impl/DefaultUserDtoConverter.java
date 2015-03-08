package org.robbins.flashcards.cassandra.repository.conversion.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.UserCassandraEntity;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.repository.conversion.impl.AbstractDtoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("userDtoConverter")
public class DefaultUserDtoConverter extends AbstractDtoConverter implements DtoConverter<UserDto, UserCassandraEntity> {

    @Override
    public UserDto getDto(final UserCassandraEntity entity) throws RepositoryException {
        return getMapper().map(entity, UserDto.class);
    }

    @Override
    public UserDto getDto(final UserCassandraEntity entity, final Set<String> fields)
            throws RepositoryException
	{
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public UserCassandraEntity getEntity(final UserDto dto) {
        return getMapper().map(dto, UserCassandraEntity.class);
    }

    @Override
    public List<UserDto> getDtos(List<UserCassandraEntity> entities) throws RepositoryException {
        List<UserDto> dtos = new ArrayList<>();
        for (UserCassandraEntity entity : entities) {
            dtos.add(getDto(entity));
        }
        return dtos;
    }

    @Override
    public List<UserDto> getDtos(final List<UserCassandraEntity> entities, final Set<String> fields)
            throws RepositoryException {throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<UserCassandraEntity> getEntities(final List<UserDto> dtos) {
        List<UserCassandraEntity> entities = new ArrayList<>();
        for (UserDto dto : dtos) {
            entities.add(getEntity(dto));
        }
        return entities;
    }
}
