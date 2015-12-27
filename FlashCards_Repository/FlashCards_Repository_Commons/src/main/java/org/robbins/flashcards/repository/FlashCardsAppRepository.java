
package org.robbins.flashcards.repository;

import org.robbins.flashcards.repository.batch.BatchSavingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface FlashCardsAppRepository<E, ID extends Serializable> extends BatchSavingRepository<E>
{

    long count();
    E save(final E entity);
    E findOne(final ID id);
    void delete(final ID id);
    void delete(final E dto);
    List<E> findAll();
    List<E> findAll(final Sort sort);
    Page<E> findAll(final Pageable page);
    List<E> findByCreatedBy_Id(final ID userId);
}
