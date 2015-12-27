
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface FlashCardSpringDataRepository extends JpaRepository<FlashCard, Long> {

    List<FlashCard> findByTagsIn(final Set<Tag> tags);
    List<FlashCard> findByTagsIn(final Set<Tag> tags, final PageRequest page);
    List<FlashCard> findByQuestionLike(final String question);
    List<FlashCard> findByQuestionLike(final String question, final PageRequest page);
    FlashCard findByQuestion(final String question);
    List<FlashCard> findByTags_Id(final Long tagId);

    @Override
    @EntityGraph(value = "FlashCard.tags", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT DISTINCT f FROM FlashCard f ORDER BY f.question")
    List<FlashCard> findAll();
}
