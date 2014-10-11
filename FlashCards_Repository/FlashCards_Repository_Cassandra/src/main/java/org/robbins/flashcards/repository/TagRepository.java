package org.robbins.flashcards.repository;

import org.robbins.flashcards.repository.domain.TagCassandra;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("tagRepository")
public interface TagRepository extends TypedIdCassandraRepository<TagCassandra, UUID> {

}
