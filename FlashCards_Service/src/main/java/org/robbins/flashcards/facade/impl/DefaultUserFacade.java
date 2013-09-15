package org.robbins.flashcards.facade.impl;

import javax.inject.Inject;

import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserFacade extends AbstractCrudFacadeImpl<User> implements UserFacade {
	
	@Inject
	private UserService service;

	public UserService getService() {
		return service;
	}
	
	public User findUserByOpenid(String openid) {
		return service.findUserByOpenid(openid);
	}
}
