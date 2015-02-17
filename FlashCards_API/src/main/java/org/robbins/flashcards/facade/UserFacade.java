
package org.robbins.flashcards.facade;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;

public interface UserFacade extends GenericCrudFacade<UserDto, String> {

    UserDto findUserByOpenid(String openid) throws FlashcardsException;
}
