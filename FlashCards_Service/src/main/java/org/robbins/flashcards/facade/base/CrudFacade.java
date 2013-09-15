package org.robbins.flashcards.facade.base;

import org.robbins.flashcards.service.base.GenericJpaService;

public interface CrudFacade<T> {
	GenericJpaService<T, Long> getService();
}
