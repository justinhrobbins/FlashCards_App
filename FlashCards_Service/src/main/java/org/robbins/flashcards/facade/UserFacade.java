package org.robbins.flashcards.facade;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.base.GenericJpaService;


public interface UserFacade extends GenericJpaService<User, Long>{
	User findUserByOpenid(String openid);
}
