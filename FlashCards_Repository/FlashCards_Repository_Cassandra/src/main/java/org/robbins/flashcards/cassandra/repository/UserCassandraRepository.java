package org.robbins.flashcards.cassandra.repository;

import org.robbins.flashcards.cassandra.repository.domain.UserCassandraEntity;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("userRepository")
public interface UserCassandraRepository extends TypedIdCassandraRepository<UserCassandraEntity, Long> {

    @Query("SELECT * FROM user WHERE openid = ?0")
    public UserCassandraEntity findUserByOpenid(final String openid);
}
