package org.robbins.flashcards.repository.springdata;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
	List<FlashCard> findByTagsIn(Set<Tag> tags);
	List<FlashCard> findByTagsIn(Set<Tag> tags, PageRequest page);
	List<FlashCard> findByQuestionLike(String question);
	List<FlashCard> findByQuestionLike(String question, PageRequest page);
	FlashCard findByQuestion(String question);
}
