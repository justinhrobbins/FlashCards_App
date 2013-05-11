package org.robbins.flashcards.service.jpa.base;

import java.util.List;

import org.robbins.flashcards.service.base.GenericJpaService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;


public abstract class AbstractCrudServiceImpl<T> implements GenericJpaService<T, Long>, CrudService<T> {

	@Override
	@Transactional
	public T save(T entity) {
		return getRepository().save(entity);
	}
	
	@Override
	public Long count() {
		return Long.valueOf(getRepository().count());
	}
	
	@Override
	public T findOne(Long id) {
		return getRepository().findOne(id);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		getRepository().delete(id);
	}
	
	@Override
	@Transactional
	public void delete(T entity) {
		getRepository().delete(entity);
	}
	
	@Override
	public List<T> findAll() {
		return getRepository().findAll();
	}

	@Override
	public List<T> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}

	@Override
	public List<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable).getContent();
	}

	@Override
	@Transactional
	public List<T> save(Iterable<T> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void flush() {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
	public T saveAndFlush(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void deleteInBatch(Iterable<T> entities) {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
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
	@Transactional
	public void delete(Iterable<? extends T> entities) {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
	public void deleteAll() {
		// TODO Auto-generated method stub
	}
}
