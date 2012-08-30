package org.robbins.flashcards.service;

import javax.inject.Inject;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericJpaServiceImpl<User, Long>  implements UserService {
	
	@Inject
	private UserRepository repository;
	
	protected UserRepository getRepository() {
		return repository;
	}
	
	@Override
	public User findUserByOpenid(String openid) {
		return getRepository().findUserByOpenid(openid);
	}
}
