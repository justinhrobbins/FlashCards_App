
package org.robbins.flashcards.repository.jpa;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.jpa.base.CrudRepository;
import org.springframework.data.domain.Pageable;

public interface FlashCardRepository extends CrudRepository<FlashCard> {

    List<FlashCard> findByTagsIn(Set<Tag> tags);

    List<FlashCard> findByTagsIn(Set<Tag> tags, Pageable page);

    List<FlashCard> findByQuestionLike(String question);

    List<FlashCard> findByQuestionLike(String question, Pageable page);

    FlashCard findByQuestion(String question);
}
