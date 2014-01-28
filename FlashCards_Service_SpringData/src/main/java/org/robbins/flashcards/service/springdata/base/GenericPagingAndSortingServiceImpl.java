
package org.robbins.flashcards.service.springdata.base;

import java.io.Serializable;
import java.util.List;

import org.robbins.flashcards.service.base.GenericPagingAndSortingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class GenericPagingAndSortingServiceImpl<T, ID extends Serializable>
        extends GenericCrudServiceImpl<T, ID> implements
        GenericPagingAndSortingService<T, ID> {

    @Override
    protected abstract PagingAndSortingRepository<T, ID> getRepository();

    @Override
    public List<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable).getContent();
    }
}
