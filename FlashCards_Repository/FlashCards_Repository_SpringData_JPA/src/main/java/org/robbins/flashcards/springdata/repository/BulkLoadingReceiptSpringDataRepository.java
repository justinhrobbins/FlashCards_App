
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.BulkLoadingReceipt;
import org.robbins.flashcards.model.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface BulkLoadingReceiptSpringDataRepository extends JpaRepository<BulkLoadingReceipt, String>, QueryDslPredicateExecutor<BulkLoadingReceipt> {
}
