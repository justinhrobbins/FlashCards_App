
package org.robbins.flashcards.facade.base;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.exceptions.FlashcardsException;

import java.util.List;
import java.util.Set;

public interface GenericCrudFacade<D> {

    List<D> list() throws FlashcardsException;
    List<D> list(Integer page, Integer size, String sort, String direction)
            throws FlashcardsException;
    List<D> list(Integer page, Integer size, String sort, String direction,
            Set<String> fields) throws FlashcardsException;
    Long count();
    D findOne(Long id) throws FlashcardsException;
    D findOne(Long id, Set<String> fields) throws FlashcardsException;
    D save(D entity) throws FlashcardsException;
    void delete(Long id);
    List<D> findByCreatedBy(final Long userId, final Set<String> fields) throws FlashcardsException;
}
