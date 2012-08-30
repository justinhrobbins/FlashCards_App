package org.robbins.flashcards.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class GenericJpaServiceImpl <T, ID extends Serializable> extends GenericPagingAndSortingServiceImpl<T, ID> implements GenericJpaService<T, ID> {

	protected abstract JpaRepository<T, ID> getRepository();
	
	public List<T> findAll() {
		return getRepository().findAll();
	}

	public List<T> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}

	public List<T> save(Iterable<T> entities) {
		return getRepository().save(entities);
	}

	public void flush() {
		getRepository().flush();
	}

	public T saveAndFlush(T entity) {
		return getRepository().saveAndFlush(entity);
	}

	public void deleteInBatch(Iterable<T> entities) {
		getRepository().deleteInBatch(entities);
	}

	public void deleteAllInBatch() {
		getRepository().deleteAllInBatch();
	}
}
