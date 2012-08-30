package org.robbins.flashcards.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class GenericPagingAndSortingServiceImpl<T, ID extends Serializable> extends GenericCrudServiceImpl<T, ID> implements GenericPagingAndSortingService<T, ID> {

	protected abstract PagingAndSortingRepository<T, ID> getRepository();
	
	public Iterable<T> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}

	public Page<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}
}
