
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.BatchLoadingReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface BatchLoadingReceiptSpringDataRepository extends JpaRepository<BatchLoadingReceipt, Long>, QueryDslPredicateExecutor<BatchLoadingReceipt> {
}
