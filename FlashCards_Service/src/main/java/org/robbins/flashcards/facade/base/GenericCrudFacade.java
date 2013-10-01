package org.robbins.flashcards.facade.base;

import java.util.List;


public interface GenericCrudFacade<D> {

	public List<D> list(Integer page, Integer size, String sort,
			String direction);
	public Long count();
	public D findOne(Long id);
	public D findOne(Long id, String fields);
	public D save(D entity);
	public void delete(Long id);
}
