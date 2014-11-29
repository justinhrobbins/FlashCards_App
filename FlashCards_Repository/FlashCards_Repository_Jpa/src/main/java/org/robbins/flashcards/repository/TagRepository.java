
package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardsAppRepository;

import java.util.List;

public interface TagRepository extends FlashCardsAppRepository<Tag, Long> {

    Tag findByName(String name);
    List<Tag> findByFlashcards_Id(Long flashcardId);
}
