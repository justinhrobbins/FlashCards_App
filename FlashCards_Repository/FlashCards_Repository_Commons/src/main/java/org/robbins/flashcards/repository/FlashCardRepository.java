
package org.robbins.flashcards.repository;

import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface FlashCardRepository<F, T, ID extends Serializable> extends FlashCardsAppRepository<F, ID> {

    List<F> findByTagsIn(final Set<T> tags);
    List<F> findByTagsIn(final Set<T> tags, final PageRequest page);
    List<F> findByQuestionLike(final String question);
    List<F> findByQuestionLike(final String question, final PageRequest page);
    F findByQuestion(final String question);
    List<F> findByTags_Id(final ID tagId);
}
