
package org.robbins.flashcards.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface FlashCardsAppRepository<E, ID extends Serializable> {

    long count();
    E save(E entity);
    E findOne(ID id);
    void delete(ID id);
    void delete(E dto);
    List<E> findAll();
    List<E> findAll(Sort sort);
    Page<E> findAll(Pageable page);
    List<E> findByCreatedBy_Id(Long userId);
}
