
package org.robbins.flashcards.service.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface GenericPagingAndSortingService<T, ID extends Serializable> extends
        GenericCrudService<T, ID> {

    List<T> findAll(Pageable pageable);
	List<T> findAll(Sort sort);
}
