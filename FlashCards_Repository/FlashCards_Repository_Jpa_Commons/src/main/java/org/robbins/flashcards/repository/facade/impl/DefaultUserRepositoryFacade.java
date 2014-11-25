
package org.robbins.flashcards.repository.facade.impl;

import javax.inject.Inject;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.robbins.flashcards.repository.conversion.DtoConverter;
import org.robbins.flashcards.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("userRepositoryFacade")
public class DefaultUserRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<UserDto, User> implements
        UserFacade {

    @Inject
    private UserRepository repository;

    @Inject
    @Qualifier("userDtoConverter")
    private DtoConverter<UserDto, User> converter;

    @Override
    public DtoConverter<UserDto, User> getConverter()
    {
        return converter;
    }

    @Override
    public UserRepository getRepository() {
		return repository;
	}

    @Override
    public UserDto findUserByOpenid(final String openid) throws RepositoryException
	{
        User result = getRepository().findUserByOpenid(openid);

        if (result == null) {
            return null;
        }

        UserDto userDto = getConverter().getDto(result);
        return userDto;
    }

    @Override
    public UserDto save(final UserDto dto) throws RepositoryException {
        User entity = getConverter().getEntity(dto);

        if (!dto.isNew()) {
            User orig = getRepository().findOne(dto.getId());
            entity.setCreatedBy(orig.getCreatedBy());
            entity.setCreatedDate(orig.getCreatedDate());
        }

        User resultEntity = getRepository().save(entity);
        UserDto resultDto = getConverter().getDto(resultEntity);
        return resultDto;
    }
}
