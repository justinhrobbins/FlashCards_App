
package org.robbins.flashcards.service.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface GenericPagingAndSortingService<T, ID extends Serializable> extends
        GenericCrudService<T, ID> {

    /**
     * Returns all entities sorted by the given options.
     *
     * @param sort
     * @return all entities sorted by the given options
     */
    Iterable<T> findAll(Sort sort);

    List<T> findAll(Pageable pageable);
}
