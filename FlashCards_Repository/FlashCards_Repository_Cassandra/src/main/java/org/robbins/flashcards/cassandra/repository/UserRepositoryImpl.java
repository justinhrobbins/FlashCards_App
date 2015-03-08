
package org.robbins.flashcards.cassandra.repository;

import org.robbins.flashcards.cassandra.repository.domain.UserCassandraEntity;
import org.robbins.flashcards.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.UUID;

@Repository
public class UserRepositoryImpl extends AbstractCrudRepositoryImpl<UserCassandraEntity, UUID> implements
        UserRepository<UserCassandraEntity, UUID> {

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
