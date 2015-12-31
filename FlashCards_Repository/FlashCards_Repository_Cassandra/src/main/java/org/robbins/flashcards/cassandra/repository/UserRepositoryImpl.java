
package org.robbins.flashcards.cassandra.repository;

import javax.inject.Inject;

import org.robbins.flashcards.cassandra.repository.domain.UserCassandraEntity;
import org.robbins.flashcards.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractCrudRepositoryImpl<UserCassandraEntity, Long> implements
        UserRepository<UserCassandraEntity, Long> {

    @Inject
    private UserCassandraRepository repository;

    @Override
    public UserCassandraRepository getRepository() {
        return repository;
    }

    @Override
    public UserCassandraEntity findUserByOpenid(final String openid) {
        return repository.findUserByOpenid(openid);
    }
}
