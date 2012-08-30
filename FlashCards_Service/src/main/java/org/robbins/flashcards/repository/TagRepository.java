package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Tag findByName(String name);
}
