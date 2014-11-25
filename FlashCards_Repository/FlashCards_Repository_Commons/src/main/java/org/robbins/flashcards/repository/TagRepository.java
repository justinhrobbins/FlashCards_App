
package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.Tag;

public interface TagRepository extends FlashCardsAppRepository<Tag, Long> {

	Tag findByName(String name);
}
