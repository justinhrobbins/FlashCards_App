package org.robbins.flashcards.service;

import org.robbins.flashcards.model.User;


public interface UserService extends GenericJpaService<User, Long>{
	
	User findUserByOpenid(String openid);
}
