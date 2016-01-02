package org.robbins.flashcards.cassandra.repository;

import org.robbins.flashcards.cassandra.repository.domain.BatchLoadingReceiptCassandraEntity;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchReceiptCassandraRepository extends TypedIdCassandraRepository<BatchLoadingReceiptCassandraEntity, Long> {

}
