
package org.robbins.flashcards.service.base;

import java.io.Serializable;

import org.robbins.flashcards.facade.base.GenericCrudFacade;


public interface CrudService<T, ID extends Serializable> {

	GenericCrudFacade<T> getFacade();
}
