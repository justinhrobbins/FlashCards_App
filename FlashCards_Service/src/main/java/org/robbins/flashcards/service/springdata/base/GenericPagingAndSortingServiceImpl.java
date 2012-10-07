package org.robbins.flashcards.service.springdata.base;

import java.io.Serializable;
import java.util.List;

import org.robbins.flashcards.service.base.GenericPagingAndSortingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class GenericPagingAndSortingServiceImpl<T, ID extends Serializable> extends GenericCrudServiceImpl<T, ID> implements GenericPagingAndSortingService<T, ID> {

	protected abstract PagingAndSortingRepository<T, ID> getRepository();
	
	public Iterable<T> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}

	public List<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable).getContent();
	}
}
