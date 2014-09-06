
package org.robbins.flashcards.service;

import javax.inject.Inject;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.robbins.flashcards.service.base.AbstractCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractCrudServiceImpl<User> implements
        UserService {

    @Inject
    private UserRepository repository;

    @Override
    public UserRepository getRepository() {
        return repository;
    }

    @Override
    public User findUserByOpenid(final String openid) {
        return getRepository().findUserByOpenid(openid);
    }
}
