package org.robbins.flashcards.service;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.base.GenericJpaService;
import org.springframework.data.domain.PageRequest;


public interface FlashCardService extends GenericJpaService<FlashCard, Long> {

	List<FlashCard> findByTagsIn(Set<Tag> tags);
	List<FlashCard> findByTagsIn(Set<Tag> tags, PageRequest page);
	List<FlashCard> findByQuestionLike(String question);
	List<FlashCard> findByQuestionLike(String question, PageRequest page);
	FlashCard findByQuestion(String question);
}
