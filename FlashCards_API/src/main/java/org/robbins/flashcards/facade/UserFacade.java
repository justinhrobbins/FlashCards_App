
package org.robbins.flashcards.facade;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;

public interface UserFacade extends GenericCrudFacade<UserDto> {

    UserDto findUserByOpenid(String openid) throws ServiceException;
}