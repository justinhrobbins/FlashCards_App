
package org.robbins.flashcards.service;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.base.GenericJpaService;

public interface TagService extends GenericJpaService<Tag, Long> {

    Tag findByName(String name);
}
