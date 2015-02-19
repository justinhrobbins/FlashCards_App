package org.robbins.flashcards.cassandra.repository;

import org.robbins.flashcards.cassandra.repository.domain.TagCassandraDto;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("tagRepository")
public interface TagRepository extends TypedIdCassandraRepository<TagCassandraDto, UUID> {

}
