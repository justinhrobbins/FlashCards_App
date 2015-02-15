
package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface FlashCardRepository extends FlashCardsAppRepository<FlashCard, Long> {

    List<FlashCard> findByTagsIn(Set<Tag> tags);
    List<FlashCard> findByTagsIn(Set<Tag> tags, PageRequest page);
    List<FlashCard> findByQuestionLike(String question);
    List<FlashCard> findByQuestionLike(String question, PageRequest page);
    FlashCard findByQuestion(String question);
    List<FlashCard> findByTags_Id(Long tagId);
}
