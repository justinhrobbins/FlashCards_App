package org.robbins.flashcards.facade.base;

import java.util.List;

import org.robbins.flashcards.service.base.GenericJpaService;

public interface CrudFacade<D, E> {
	GenericJpaService<E, Long> getService();
	
	D getDto(E entity);
	E getEntity(D dto);
	List<D> getDtos(List<E> entities);
	List<E> getEtnties(List<D> entities);
}
