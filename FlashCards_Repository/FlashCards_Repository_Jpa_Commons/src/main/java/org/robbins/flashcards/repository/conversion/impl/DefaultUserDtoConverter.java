package org.robbins.flashcards.repository.conversion.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.repository.util.DtoUtil;
import org.springframework.stereotype.Component;

@Component("userDtoConverter")
public class DefaultUserDtoConverter extends AbstractDtoConverter implements DtoConverter<UserDto, User> {
    @Override
    public UserDto getDto(final User entity) throws RepositoryException {
        return getDto(entity, null);
    }

    @Override
    public UserDto getDto(final User entity, final Set<String> fields)
            throws RepositoryException
	{
        UserDto userDto = getMapper().map(entity, UserDto.class);
        DtoUtil.filterFields(userDto, fields);
        return userDto;
    }

    @Override
    public User getEntity(final UserDto dto) {
        return getMapper().map(dto, User.class);
    }

    @Override
    public List<UserDto> getDtos(List<User> entities) throws RepositoryException {
        return getDtos(entities, null);
    }

    @Override
    public List<UserDto> getDtos(final List<User> entities, final Set<String> fields)
            throws RepositoryException {
        List<UserDto> dtos = new ArrayList<>();
        for (User entity : entities) {
            dtos.add(getDto(entity, fields));
        }
        return dtos;
    }

    @Override
    public List<User> getEntities(final List<UserDto> dtos) {
        List<User> entities = new ArrayList<>();
        for (UserDto dto : dtos) {
            entities.add(getEntity(dto));
        }
        return entities;
    }
}
