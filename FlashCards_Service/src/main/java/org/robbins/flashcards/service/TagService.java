
package org.robbins.flashcards.service;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;

public interface TagService extends GenericPagingAndSortingService<TagDto, Long>
{

    TagDto findByName(String name);
}
