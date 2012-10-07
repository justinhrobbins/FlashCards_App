package org.robbins.flashcards.service.springdata.base;

import java.io.Serializable;

import org.robbins.flashcards.service.base.GenericCrudService;
import org.springframework.data.repository.CrudRepository;

public abstract class GenericCrudServiceImpl<T, ID extends Serializable> implements GenericCrudService<T, ID> {

	protected abstract CrudRepository<T, ID> getRepository();
	
	public T save(T entity) {
		return getRepository().save(entity);
	}

	public Iterable<T> save(Iterable<T> entities) {
		return getRepository().save(entities);
	}

	public T findOne(ID id) {
		return getRepository().findOne(id);
	}

	public boolean exists(ID id) {
		return getRepository().exists(id);
	}

	public Iterable<T> findAll() {
		return getRepository().findAll();
	}

	public Iterable<T> findAll(Iterable<ID> ids) {
		return getRepository().findAll(ids);
	}

	public long count() {
		return getRepository().count();
	}

	public void delete(ID id) {
		getRepository().delete(id);
	}

	public void delete(T entity) {
		getRepository().delete(entity);
	}

	public void delete(Iterable<? extends T> entities) {
		getRepository().delete(entities);
	}

	public void deleteAll() {
		getRepository().deleteAll();
	}
}
