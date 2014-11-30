
package org.robbins.flashcards.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface FlashCardsAppRepository<T, ID extends Serializable> {

    long count();
    T save(T entity);
    T findOne(ID id);
    void delete(ID id);
    void delete(T entity);
    List<T> findAll();
    List<T> findAll(Sort sort);
    Page<T> findAll(Pageable page);
    List<T> findByCreatedBy_Id(Long userId);
}
