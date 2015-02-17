
package org.robbins.flashcards.facade.base;

import org.robbins.flashcards.exceptions.FlashcardsException;

import java.util.List;
import java.util.Set;

public interface GenericCrudFacade<D, ID> {

    List<D> list() throws FlashcardsException;
    List<D> list(Integer page, Integer size, String sort, String direction)
            throws FlashcardsException;
    List<D> list(Integer page, Integer size, String sort, String direction,
            Set<String> fields) throws FlashcardsException;
    Long count();
    D findOne(final ID id) throws FlashcardsException;
    D findOne(final ID id, Set<String> fields) throws FlashcardsException;
    D save(final D entity) throws FlashcardsException;
    void delete(final ID id);
    List<D> findByCreatedBy(final ID userId, final Set<String> fields) throws FlashcardsException;
}
