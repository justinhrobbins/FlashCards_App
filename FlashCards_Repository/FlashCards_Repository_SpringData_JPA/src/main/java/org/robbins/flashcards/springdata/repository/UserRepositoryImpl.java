
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class UserRepositoryImpl extends AbstractCrudRepositoryImpl<User> implements
        UserRepository {

    @Inject
    private UserSpringDataRepository repository;

    @Override
    public UserSpringDataRepository getRepository() {
        return repository;
    }

    @Override
    public User findUserByOpenid(final String openid) {
        return repository.findUserByOpenid(openid);
    }
}
