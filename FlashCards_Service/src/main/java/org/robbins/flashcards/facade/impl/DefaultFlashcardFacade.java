package org.robbins.flashcards.facade.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.FlashCardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class DefaultFlashcardFacade extends AbstractCrudFacadeImpl<FlashCard> implements FlashcardFacade {
	
	@Inject
	private FlashCardService service;

	public FlashCardService getService() {
		return service;
	}
	
	public List<FlashCard> findByTagsIn(Set<Tag> tags) {
		return service.findByTagsIn(tags);
	}
	public List<FlashCard> findByTagsIn(Set<Tag> tags, PageRequest page) {
		return service.findByTagsIn(tags, page);
	}
	public List<FlashCard> findByQuestionLike(String question) {
		return service.findByQuestionLike(question);
	}
	public List<FlashCard> findByQuestionLike(String question, PageRequest page) {
		return service.findByQuestionLike(question, page);
	}
	public FlashCard findByQuestion(String question) {
		return service.findByQuestion(question);
	}
}
