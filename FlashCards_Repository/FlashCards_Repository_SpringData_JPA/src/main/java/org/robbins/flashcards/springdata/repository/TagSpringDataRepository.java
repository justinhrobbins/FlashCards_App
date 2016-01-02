
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface TagSpringDataRepository extends JpaRepository<Tag, Long>, QueryDslPredicateExecutor<Tag> {

    Tag findByName(String name);
    List<Tag> findByFlashCards_Id(String flashCardId);

    @EntityGraph(value = "Tag.flashCards", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT DISTINCT t FROM Tag t ORDER BY t.name")
    List<Tag> findAll();
}
