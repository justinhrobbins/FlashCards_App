package org.robbins.flashcards.service.jpa;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.jpa.FlashCardRepository;
import org.robbins.flashcards.service.FlashCardService;
import org.robbins.flashcards.service.jpa.base.AbstractCrudServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FlashCardServiceImpl extends AbstractCrudServiceImpl<FlashCard> implements FlashCardService {

	@Inject
	private FlashCardRepository repository;

	public FlashCardRepository getRepository() {
		return repository;
	}
	@Override
	public List<FlashCard> findByTagsIn(Set<Tag> tags) {
		return getRepository().findByTagsIn(tags);
	}

	@Override
	public List<FlashCard> findByTagsIn(Set<Tag> tags, PageRequest page) {
		return getRepository().findByTagsIn(tags, page);
	}

	@Override
	public List<FlashCard> findByQuestionLike(String question) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlashCard> findByQuestionLike(String question, PageRequest page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlashCard findByQuestion(String question) {
		// TODO Auto-generated method stub
		return null;
	}
}
