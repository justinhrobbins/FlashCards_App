package org.robbins.flashcards.cassandra.repository;

import org.robbins.flashcards.cassandra.repository.domain.UserCassandraDto;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("userRepository")
public interface UserRepository extends TypedIdCassandraRepository<UserCassandraDto, UUID> {

    @Query("SELECT * FROM user WHERE openid = ?0")
    public UserCassandraDto findUserByOpenid(final String openid);
}
