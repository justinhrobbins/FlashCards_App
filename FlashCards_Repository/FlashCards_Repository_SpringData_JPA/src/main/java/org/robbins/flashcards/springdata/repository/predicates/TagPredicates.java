package org.robbins.flashcards.springdata.repository.predicates;

import com.mysema.query.types.Predicate;
import org.robbins.flashcards.model.QTag;

public class TagPredicates {

    public static Predicate hasName(final String name) {
        QTag tag = QTag.tag;
        return tag.name.eq(name);
    }

    public static Predicate hasFlashCardId(final Long flashCardId) {
        QTag tag = QTag.tag;
        return tag.flashCards.any().id.eq(flashCardId);
    }
}
