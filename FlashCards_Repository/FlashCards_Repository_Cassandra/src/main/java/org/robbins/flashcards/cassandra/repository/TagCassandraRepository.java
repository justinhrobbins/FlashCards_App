package org.robbins.flashcards.cassandra.repository;

import org.robbins.flashcards.cassandra.repository.domain.TagCassandraDto;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagCassandraRepository extends TypedIdCassandraRepository<TagCassandraDto, UUID> {

    @Query("SELECT * FROM tag WHERE name = ?0")
    public TagCassandraDto findByName(final String name);
}
