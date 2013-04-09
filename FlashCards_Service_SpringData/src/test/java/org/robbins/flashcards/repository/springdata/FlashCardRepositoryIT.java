package org.robbins.flashcards.repository.springdata;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.tests.BaseIntegrationTest;

import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("flashCardsAppRepository.xml")
public class FlashCardRepositoryIT extends BaseIntegrationTest {

	@Inject
	FlashCardRepository repository;

	@Test
	public void findByQuestion() {
		FlashCard flashCard = repository.findByQuestion("question1"); 
		assertThat(flashCard, is(instanceOf(FlashCard.class)));
	}
	
	@Test
	public void createNewFlashCard_WithNewTag() {
		FlashCard flashCard = new FlashCard();
		flashCard.setQuestion("Question2");
		flashCard.setAnswer("Answer2");
		
		Tag tag = new Tag();
		tag.setName("tag3");
		
		flashCard.getTags().add(tag);
		
		repository.save(flashCard);
		
		assertThat(flashCard.getId(), greaterThan(0L));
	}
}
