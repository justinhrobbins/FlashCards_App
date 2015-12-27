package org.robbins.flashcards.cassandra.repository;

import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlashCardCassandraRepository extends TypedIdCassandraRepository<FlashCardCassandraEntity, Long> {

}
