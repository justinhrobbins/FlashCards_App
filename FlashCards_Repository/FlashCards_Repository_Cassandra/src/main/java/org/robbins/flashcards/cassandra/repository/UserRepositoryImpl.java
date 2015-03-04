
package org.robbins.flashcards.cassandra.repository;

import org.robbins.flashcards.cassandra.repository.domain.UserCassandraDto;
import org.robbins.flashcards.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.UUID;

@Repository
public class UserRepositoryImpl extends AbstractCrudRepositoryImpl<UserCassandraDto, UUID> implements
        UserRepository<UserCassandraDto, UUID> {

    @Inject
    private UserCassandraRepository repository;

    @Override
    public UserCassandraRepository getRepository() {
        return repository;
    }

    @Override
    public UserCassandraDto findUserByOpenid(final String openid) {
        return repository.findUserByOpenid(openid);
    }
}
