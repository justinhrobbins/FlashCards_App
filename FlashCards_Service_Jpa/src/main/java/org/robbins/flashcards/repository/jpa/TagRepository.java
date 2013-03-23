package org.robbins.flashcards.repository.jpa;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.jpa.base.CrudRepository;

public interface TagRepository extends CrudRepository<Tag> {
	Tag findByName(String name);
}
