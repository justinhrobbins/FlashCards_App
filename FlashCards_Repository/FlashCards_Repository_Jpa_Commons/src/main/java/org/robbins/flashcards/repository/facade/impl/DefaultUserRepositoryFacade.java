
package org.robbins.flashcards.repository.facade.impl;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.robbins.flashcards.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Component("userRepositoryFacade")
public class DefaultUserRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<UserDto, User, Long> implements
        UserFacade {

    @Inject
    private UserRepository<User, Long> repository;

    @Inject
    @Qualifier("userDtoConverter")
    private DtoConverter<UserDto, User> converter;

    @Override
    public DtoConverter<UserDto, User> getConverter()
    {
        return converter;
    }

    @Override
    public UserRepository<User, Long> getRepository() {
		return repository;
	}

    @Override
    public UserDto findUserByOpenid(final String openid) throws RepositoryException
	{
        final User result = repository.findUserByOpenid(openid);
        if (result == null) {
            return null;
        }
        return convertAndInitializeEntity(result);
    }

    @Override
    public UserDto save(final UserDto dto) throws RepositoryException {
        final User entity = getConverter().getEntity(dto);

        if (!dto.isNew()) {
            final User orig = repository.findOne(dto.getId());
            entity.setCreatedBy(orig.getCreatedBy());
            entity.setCreatedDate(orig.getCreatedDate());
        }
        configureCreatedByAndTime(entity);
        final User result = getRepository().save(entity);
        return convertAndInitializeEntity(result);
    }
}
