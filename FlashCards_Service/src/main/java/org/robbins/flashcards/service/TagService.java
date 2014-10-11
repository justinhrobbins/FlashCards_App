
package org.robbins.flashcards.service;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;

public interface TagService extends GenericPagingAndSortingService<Tag, Long>
{

    Tag findByName(String name);
}
