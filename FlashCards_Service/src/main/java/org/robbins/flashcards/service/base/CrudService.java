
package org.robbins.flashcards.service.base;

import org.robbins.flashcards.facade.base.GenericCrudFacade;

import java.io.Serializable;


public interface CrudService<T, ID extends Serializable> {

	GenericCrudFacade<T, ID> getFacade();
}
