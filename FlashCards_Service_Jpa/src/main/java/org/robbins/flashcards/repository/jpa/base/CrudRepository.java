
package org.robbins.flashcards.repository.jpa.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CrudRepository<T> {

    Long count();

    T save(T entity);

    T findOne(Long id);

    void delete(Long id);

    void delete(T entity);

    List<T> findAll();

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable page);
}
