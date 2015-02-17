
package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;

public interface FlashCardRepository extends FlashCardsAppRepository<FlashCard, String> {

    List<FlashCard> findByTagsIn(final Set<Tag> tags);
    List<FlashCard> findByTagsIn(final Set<Tag> tags, final PageRequest page);
    List<FlashCard> findByQuestionLike(final String question);
    List<FlashCard> findByQuestionLike(final String question, final PageRequest page);
    FlashCard findByQuestion(final String question);
    List<FlashCard> findByTags_Id(final String tagId);
}
