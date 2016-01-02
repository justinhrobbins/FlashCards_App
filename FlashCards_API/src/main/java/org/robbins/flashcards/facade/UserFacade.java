
package org.robbins.flashcards.facade;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;

public interface UserFacade extends GenericCrudFacade<UserDto, Long> {

    UserDto findUserByOpenid(String openid) throws FlashCardsException;
}
