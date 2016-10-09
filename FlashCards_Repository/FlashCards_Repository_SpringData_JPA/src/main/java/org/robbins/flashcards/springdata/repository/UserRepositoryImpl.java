
package org.robbins.flashcards.springdata.repository;

import static org.robbins.flashcards.springdata.repository.predicates.UserPredicates.hasOpenId;

import java.util.List;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.inject.Inject;

import org.robbins.flashcards.caching.PersistableCacheKeyGenerator;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.springframework.stereotype.Repository;

@CacheDefaults(cacheKeyGenerator = PersistableCacheKeyGenerator.class)
@Repository
public class UserRepositoryImpl extends AbstractCrudRepositoryImpl<User, Long> implements
        UserRepository<User, Long> {

    @Inject
    private UserSpringDataRepository repository;

    @Override
    public UserSpringDataRepository getRepository() {
        return repository;
    }

    @Override
    @CacheResult(cacheName = "userByOpenId")
    public User findUserByOpenid(final String openid) {
        return repository.findOne(hasOpenId(openid));
    }

    @Override
    @CacheResult(cacheName = "userById")
    public User findOne(final Long id) {
        return repository.findOne(id);
    }

    @Override
    @CacheRemove(cacheName = "userById")
    public User save(final User entity) {
        return repository.save(entity);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
