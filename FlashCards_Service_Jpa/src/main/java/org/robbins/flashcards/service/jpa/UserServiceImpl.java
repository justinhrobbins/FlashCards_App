
package org.robbins.flashcards.service.jpa;

import javax.inject.Inject;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.jpa.UserRepository;
import org.robbins.flashcards.service.UserService;
import org.robbins.flashcards.service.jpa.base.AbstractCrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends AbstractCrudServiceImpl<User> implements UserService {

    @Inject
    private UserRepository repository;

    @Override
    public UserRepository getRepository() {
        return repository;
    }

    @Override
    public User findUserByOpenid(String openid) {
        return getRepository().findUserByOpenid(openid);
    }
}
