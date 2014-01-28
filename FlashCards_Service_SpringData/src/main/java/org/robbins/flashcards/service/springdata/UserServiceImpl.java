
package org.robbins.flashcards.service.springdata;

import javax.inject.Inject;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.springdata.UserRepository;
import org.robbins.flashcards.service.UserService;
import org.robbins.flashcards.service.springdata.base.GenericJpaServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericJpaServiceImpl<User, Long> implements
        UserService {

    @Inject
    private UserRepository repository;

    @Override
    protected UserRepository getRepository() {
        return repository;
    }

    @Override
    public User findUserByOpenid(String openid) {
        return getRepository().findUserByOpenid(openid);
    }
}
