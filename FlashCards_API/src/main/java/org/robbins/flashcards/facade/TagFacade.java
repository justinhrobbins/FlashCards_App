
package org.robbins.flashcards.facade;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;

public interface TagFacade extends GenericCrudFacade<TagDto> {

    TagDto findByName(String name) throws ServiceException;
}