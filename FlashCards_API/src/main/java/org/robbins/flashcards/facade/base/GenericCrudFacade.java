
package org.robbins.flashcards.facade.base;

import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenericCrudFacade<D, ID> {

    List<D> list() throws FlashCardsException;
    List<D> list(final Optional<Pageable> page) throws FlashCardsException;
    List<D> list(final Optional<Pageable> page, Set<String> fields) throws FlashCardsException;
    Long count();
    D findOne(final ID id) throws FlashCardsException;
    D findOne(final ID id, Set<String> fields) throws FlashCardsException;
    D save(final D entity) throws FlashCardsException;
    BatchLoadingReceiptDto save(final List<D> dtos) throws FlashCardsException;
    void delete(final ID id);
    List<D> findByCreatedBy(final ID userId, final Set<String> fields) throws FlashCardsException;
}
