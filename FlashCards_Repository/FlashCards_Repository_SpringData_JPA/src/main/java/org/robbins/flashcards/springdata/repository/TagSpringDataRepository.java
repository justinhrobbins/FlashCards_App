
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagSpringDataRepository extends JpaRepository<Tag, String> {

    Tag findByName(String name);
    List<Tag> findByFlashcards_Id(String flashcardId);
}
