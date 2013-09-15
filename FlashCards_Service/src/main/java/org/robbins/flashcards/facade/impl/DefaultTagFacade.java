package org.robbins.flashcards.facade.impl;

import javax.inject.Inject;

import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagService;
import org.springframework.stereotype.Component;

@Component
public class DefaultTagFacade extends AbstractCrudFacadeImpl<Tag> implements TagFacade {
	
	@Inject
	private TagService service;

	public TagService getService() {
		return service;
	}
	
	public Tag findByName(String name) {
		return service.findByName(name);
	}
}
