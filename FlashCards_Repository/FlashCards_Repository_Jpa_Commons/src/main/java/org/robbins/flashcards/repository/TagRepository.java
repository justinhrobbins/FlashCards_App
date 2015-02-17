
package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.Tag;

import java.util.List;

public interface TagRepository extends FlashCardsAppRepository<Tag, String> {

	Tag findByName(final String name);
	List<Tag> findByFlashcards_Id(final String flashcardId);
}
