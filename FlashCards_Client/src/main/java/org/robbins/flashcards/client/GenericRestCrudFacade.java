package org.robbins.flashcards.client;

import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;

public interface GenericRestCrudFacade<E> extends GenericCrudFacade<E> {

    void put(E entity) throws ServiceException;
    void update(E entity) throws ServiceException;
}
