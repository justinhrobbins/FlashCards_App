package org.robbins.flashcards.service.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.jpa.FlashCardRepository;
import org.robbins.flashcards.service.jpa.FlashCardServiceImpl;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FlashCardServiceImplUT extends BaseMockingTest {

	@Mock FlashCardRepository repository;
	FlashCardServiceImpl flashCardService;

	@Before
	public void before() {
		flashCardService = new FlashCardServiceImpl();
		ReflectionTestUtils.setField(flashCardService, "repository", repository);		
	}
	
	@Test
	public void testFindByQuestion() {
		when(repository.findByQuestion(Mockito.anyString())).thenReturn(mock(FlashCard.class));
		
		FlashCard flashCard = flashCardService.findByQuestion("Question");

		Mockito.verify(repository, Mockito.times(1)).findByQuestion("Question");
		assertThat(flashCard, is(FlashCard.class));
	}
	
	@Test
	public void testFindByQuestionLike() {
		when(repository.findByQuestionLike(Mockito.anyString())).thenReturn(new ArrayList<FlashCard>());
		
		List<FlashCard> flashCards = flashCardService.findByQuestionLike("Question");
		
		Mockito.verify(repository, Mockito.times(1)).findByQuestionLike("Question");
		assertThat(flashCards, is(List.class));
	}
	
	@Test
	public void testFindByTagsIn() {
		when(repository.findByTagsIn(new HashSet<Tag>())).thenReturn(new ArrayList<FlashCard>());
		
		Set<Tag> tags = new HashSet<Tag>();
		tags.add(new Tag("EJB"));

		List<FlashCard> flashCards = flashCardService.findByTagsIn(tags);
		
		Mockito.verify(repository, Mockito.times(1)).findByTagsIn(tags);
		assertThat(flashCards, is(List.class));
	}
	
	@Test
	public void testFindByTagsInWithPageRequest() {
		when(repository.findByTagsIn(new HashSet<Tag>(), mock(PageRequest.class))).thenReturn(new ArrayList<FlashCard>());

		Set<Tag> tags = new HashSet<Tag>();
		tags.add(new Tag("EJB"));
		PageRequest page = mock(PageRequest.class);

		List<FlashCard> flashCards = flashCardService.findByTagsIn(tags, page);
		
		Mockito.verify(repository, Mockito.times(1)).findByTagsIn(tags, page);
		assertThat(flashCards, is(List.class));
	}
	
	@Test
	public void testFindByQuestionLikeWithPageRequest() {
		when(repository.findByQuestionLike("Question", mock(PageRequest.class))).thenReturn(new ArrayList<FlashCard>());

		PageRequest page = mock(PageRequest.class);

		List<FlashCard> flashCards = flashCardService.findByQuestionLike("Question", page);

		Mockito.verify(repository, Mockito.times(1)).findByQuestionLike("Question", page);
		assertThat(flashCards, is(List.class));	
	}
}
