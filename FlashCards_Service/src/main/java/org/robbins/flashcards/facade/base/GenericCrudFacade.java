package org.robbins.flashcards.facade.base;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.exceptions.ServiceException;


public interface GenericCrudFacade<D> {

	public List<D> list(Integer page, Integer size, String sort,
			String direction) throws ServiceException;
	public List<D> list(Integer page, Integer size, String sort,
			String direction, Set<String> fields) throws ServiceException;
	public Long count();
	public D findOne(Long id) throws ServiceException;
	public D findOne(Long id, Set<String> fields) throws ServiceException;
	public D save(D entity);
	public void delete(Long id);
}
