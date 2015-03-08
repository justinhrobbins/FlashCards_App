package org.robbins.flashcards.cassandra.repository;

import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagCassandraRepository extends TypedIdCassandraRepository<TagCassandraEntity, UUID> {

    @Query("SELECT * FROM tag WHERE name = ?0")
    public TagCassandraEntity findByName(final String name);
}
