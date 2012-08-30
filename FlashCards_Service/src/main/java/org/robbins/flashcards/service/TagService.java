package org.robbins.flashcards.service;

import org.robbins.flashcards.model.Tag;

public interface TagService extends GenericJpaService<Tag, Long> {
	
	Tag findByName(String name);
}
