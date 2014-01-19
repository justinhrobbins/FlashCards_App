
package org.robbins.flashcards.facade;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.facade.base.CrudFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.model.User;

public interface UserFacade extends GenericCrudFacade<UserDto>, CrudFacade<UserDto, User> {

    UserDto findUserByOpenid(String openid);
}
