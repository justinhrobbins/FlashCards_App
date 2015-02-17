
package org.robbins.flashcards.service;

import javax.inject.Inject;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.service.base.AbstractCrudServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractCrudServiceImpl<UserDto, String> implements
        UserService {

    @Inject
	@Qualifier("userRepositoryFacade")
    private UserFacade facade;

    @Override
    public GenericCrudFacade<UserDto, String> getFacade() {
        return facade;
    }

    @Override
    public UserDto findUserByOpenid(final String openid) throws FlashcardsException {
        return facade.findUserByOpenid(openid);
    }
}
