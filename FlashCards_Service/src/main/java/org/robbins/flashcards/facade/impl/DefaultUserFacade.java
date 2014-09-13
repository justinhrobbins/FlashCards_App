
package org.robbins.flashcards.facade.impl;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Component
public class DefaultUserFacade extends AbstractCrudFacadeImpl<UserDto, User> implements
        UserFacade {

    @Inject
    private UserService service;

    @Inject
    @Qualifier("userDtoConverter")
    private DtoConverter<UserDto, User> converter;

    @Override
    public DtoConverter<UserDto, User> getConverter()
    {
        return converter;
    }

    @Override
    public UserService getService() {
        return service;
    }

    @Override
    public UserDto findUserByOpenid(final String openid) throws ServiceException {
        User result = service.findUserByOpenid(openid);

        if (result == null) {
            return null;
        }

        UserDto userDto = getConverter().getDto(result);
        return userDto;
    }

    @Override
    public UserDto save(final UserDto dto) throws ServiceException {
        User entity = getConverter().getEntity(dto);

        if (!dto.isNew()) {
            User orig = service.findOne(dto.getId());
            entity.setCreatedBy(orig.getCreatedBy());
            entity.setCreatedDate(orig.getCreatedDate());
        }

        User resultEntity = getService().save(entity);
        UserDto resultDto = getConverter().getDto(resultEntity);
        return resultDto;
    }
}
