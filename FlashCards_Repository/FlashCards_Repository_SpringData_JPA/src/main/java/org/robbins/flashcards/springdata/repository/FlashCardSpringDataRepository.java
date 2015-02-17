
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface FlashCardSpringDataRepository extends JpaRepository<FlashCard, String> {

    List<FlashCard> findByTagsIn(final Set<Tag> tags);
    List<FlashCard> findByTagsIn(final Set<Tag> tags, final PageRequest page);
    List<FlashCard> findByQuestionLike(final String question);
    List<FlashCard> findByQuestionLike(final String question, final PageRequest page);
    FlashCard findByQuestion(final String question);
    List<FlashCard> findByTags_Id(final String tagId);
}
