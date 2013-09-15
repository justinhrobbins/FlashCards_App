package org.robbins.flashcards.facade;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.base.GenericJpaService;

public interface TagFacade extends GenericJpaService<Tag, Long> {
	Tag findByName(String name);
}
