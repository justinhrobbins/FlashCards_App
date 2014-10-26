
package org.robbins.flashcards.presentation.facade.impl;

import javax.inject.Inject;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.presentation.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.service.UserService;
import org.springframework.stereotype.Component;

@Component("presentationUserFacade")
public class DefaultUserFacade extends AbstractCrudFacadeImpl<UserDto> implements
        UserFacade {

    @Inject
    private UserService service;

    @Override
    public UserService getService() {
        return service;
    }

    @Override
    public UserDto findUserByOpenid(final String openid) throws FlashcardsException {
        return service.findUserByOpenid(openid);
    }

    @Override
    public UserDto save(final UserDto dto) throws FlashcardsException
	{
        return  getService().save(dto);
    }
}
