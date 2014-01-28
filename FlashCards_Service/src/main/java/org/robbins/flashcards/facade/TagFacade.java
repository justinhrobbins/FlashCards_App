
package org.robbins.flashcards.facade;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.facade.base.CrudFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.model.Tag;

public interface TagFacade extends GenericCrudFacade<TagDto>, CrudFacade<TagDto, Tag> {

    TagDto findByName(String name);
}
