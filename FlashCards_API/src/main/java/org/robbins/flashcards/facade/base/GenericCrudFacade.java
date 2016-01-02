
package org.robbins.flashcards.facade.base;

import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenericCrudFacade<D, ID> {

    List<D> list() throws FlashcardsException;
    List<D> list(final Optional<Pageable> page) throws FlashcardsException;
    List<D> list(final Optional<Pageable> page, Set<String> fields) throws FlashcardsException;
    Long count();
    D findOne(final ID id) throws FlashcardsException;
    D findOne(final ID id, Set<String> fields) throws FlashcardsException;
    D save(final D entity) throws FlashcardsException;
    BatchLoadingReceiptDto save(final List<D> dtos) throws FlashcardsException;
    void delete(final ID id);
    List<D> findByCreatedBy(final ID userId, final Set<String> fields) throws FlashcardsException;
}
