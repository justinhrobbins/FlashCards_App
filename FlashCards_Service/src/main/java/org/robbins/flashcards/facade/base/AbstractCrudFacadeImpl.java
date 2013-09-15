package org.robbins.flashcards.facade.base;

import java.util.List;

import org.robbins.flashcards.service.base.GenericJpaService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public abstract class AbstractCrudFacadeImpl<T> implements GenericJpaService<T, Long>, CrudFacade<T> {

	@Override
	public T save(T entity) {
		return getService().save(entity);
	}
	
	@Override
	public Long count() {
		return Long.valueOf(getService().count());
	}
	
	@Override
	public T findOne(Long id) {
		return getService().findOne(id);
	}
	
	@Override
	public void delete(Long id) {
		getService().delete(id);
	}
	
	@Override
	public void delete(T entity) {
		getService().delete(entity);
	}
	
	@Override
	public List<T> findAll() {
		return getService().findAll();
	}

	@Override
	public List<T> findAll(Sort sort) {
		return getService().findAll(sort);
	}

	@Override
	public List<T> findAll(Pageable pageable) {
		return getService().findAll(pageable);
	}

	@Override
	public List<T> save(Iterable<T> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
	}

	@Override
	public T saveAndFlush(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<T> entities) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<T> findAll(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
	}
}
