
package org.robbins.flashcards.service;

import javax.inject.Inject;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.service.base.AbstractCrudServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractCrudServiceImpl<UserDto, Long> implements
        UserService {

    @Inject
	@Qualifier("userRepositoryFacade")
    private UserFacade facade;

    @Override
    public GenericCrudFacade<UserDto, Long> getFacade() {
        return facade;
    }

    @Override
    public UserDto findUserByOpenid(final String openid) throws FlashCardsException
    {
        return facade.findUserByOpenid(openid);
    }
}
