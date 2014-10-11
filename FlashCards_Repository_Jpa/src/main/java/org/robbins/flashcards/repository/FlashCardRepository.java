
package org.robbins.flashcards.repository;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.springframework.data.domain.Pageable;

public interface FlashCardRepository extends FlashCardsAppRepository<FlashCard, Long> {

    List<FlashCard> findByTagsIn(Set<Tag> tags);

    List<FlashCard> findByTagsIn(Set<Tag> tags, Pageable page);

    List<FlashCard> findByQuestionLike(String question);

    List<FlashCard> findByQuestionLike(String question, Pageable page);

    FlashCard findByQuestion(String question);
}