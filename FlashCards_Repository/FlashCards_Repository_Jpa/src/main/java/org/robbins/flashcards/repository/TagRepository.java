
package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardsAppRepository;

public interface TagRepository extends FlashCardsAppRepository<Tag, Long> {

    Tag findByName(String name);
}
