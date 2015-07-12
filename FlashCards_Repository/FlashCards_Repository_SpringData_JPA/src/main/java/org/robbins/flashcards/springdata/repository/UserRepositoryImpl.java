
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

import java.util.List;

import static org.robbins.flashcards.springdata.repository.predicates.UserPredicates.hasOpenId;

@Repository
public class UserRepositoryImpl extends AbstractCrudRepositoryImpl<User, String> implements
        UserRepository<User, String> {

    @Inject
    private UserSpringDataRepository repository;

    @Override
    public UserSpringDataRepository getRepository() {
        return repository;
    }

    @Override
    @Cacheable("userByOpenId")
    public User findUserByOpenid(final String openid) {
        return repository.findOne(hasOpenId(openid));
    }

    @Override
    @Cacheable("userById")
    public User findOne(String id) {
        return repository.findOne(id);
    }

    @Override
    @CacheEvict(value = "userById", key = "#p0.id")
    public User save(User entity) {
        return repository.save(entity);
    }

    @Override
    @Cacheable("users")
    public List<User> findAll() {
        return repository.findAll();
    }
}
