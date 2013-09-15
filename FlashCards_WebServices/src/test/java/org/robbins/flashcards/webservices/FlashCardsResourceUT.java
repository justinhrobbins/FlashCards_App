package org.robbins.flashcards.webservices;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FlashCardsResourceUT extends BaseMockingTest {

	@Mock
	FlashcardFacade flashcardFacade;

	@Mock
	TagFacade tagFacade;
	
	@Mock
	FlashCard flashcard;
	
	FlashCardsResource resource;
	
	@Before
	public void before() {
		resource = new FlashCardsResource();
		ReflectionTestUtils.setField(resource, "flashcardFacade", flashcardFacade);
		ReflectionTestUtils.setField(resource, "tagFacade", tagFacade);
	}

	@Test
	public void searchByQuestionWithPaging() {
		when(flashcardFacade.findByQuestionLike(any(String.class), any(PageRequest.class))).thenReturn(new ArrayList<FlashCard>());
		
		FlashCard[] results = resource.search(0, 1, "Question", null);
		
		verify(flashcardFacade).findByQuestionLike(any(String.class), any(PageRequest.class));
		assertThat(results, is(FlashCard[].class));
	}
	
	@Test
	public void searchByQuestionNoPaging() {
		when(flashcardFacade.findByQuestionLike(any(String.class))).thenReturn(new ArrayList<FlashCard>());
		
		FlashCard[] results = resource.search(null, null, "Question", null);
		
		verify(flashcardFacade).findByQuestionLike(any(String.class));
		assertThat(results, is(FlashCard[].class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void searchByTagsWithPaging() {
		when(flashcardFacade.findByTagsIn(any(Set.class), any(PageRequest.class))).thenReturn(new ArrayList<FlashCard>());
		
		FlashCard[] results = resource.search(0, 1, null, "1,2");
		
		verify(flashcardFacade).findByTagsIn(any(Set.class), any(PageRequest.class));
		assertThat(results, is(FlashCard[].class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void searchByTagsNoPaging() {
		when(flashcardFacade.findByTagsIn(any(Set.class))).thenReturn(new ArrayList<FlashCard>());
		
		FlashCard[] results = resource.search(null, null, null, "1,2");
		
		verify(flashcardFacade).findByTagsIn(any(Set.class));
		assertThat(results, is(FlashCard[].class));
	}

	@Test(expected = WebApplicationException.class)
	public void searchWithBadRequest() {
		resource.search(null, null, null, null);
	}
	
	@Test
	public void searchCount() {
		when(flashcardFacade.findByQuestionLike(any(String.class))).thenReturn(new ArrayList<FlashCard>());
		
		Long results = resource.searchCount("Question", null);
		
		verify(flashcardFacade).findByQuestionLike(any(String.class));
		assertThat(results, is(Long.class));
	}
	
	@Test
	public void put() {
		when(flashcardFacade.findOne(any(Long.class))).thenReturn(flashcard);
		when(flashcardFacade.save(any(FlashCard.class))).thenReturn(flashcard);
		
		Response response = resource.put(1L, flashcard);
		
		verify(flashcardFacade).findOne(any(Long.class));
		verify(flashcardFacade).save(any(FlashCard.class));
		assertThat(response.getStatus(), is(204));
	}
	
	@Test
	public void postNoTags() {
		when(flashcardFacade.save(any(FlashCard.class))).thenReturn(flashcard);
		
		FlashCard result = resource.post(flashcard);
		
		verify(flashcardFacade).save(any(FlashCard.class));
		assertThat(result, is(FlashCard.class));
	}
	
	@Test
	public void postWithTagByName() {
		when(tagFacade.findByName(any(String.class))).thenReturn(new Tag());
		when(flashcardFacade.save(any(FlashCard.class))).thenReturn(flashcard);
		
		FlashCard flashcard = new FlashCard();
		flashcard.getTags().add(new Tag("tag1"));
		FlashCard result = resource.post(flashcard);
		
		verify(tagFacade).findByName(any(String.class));
		verify(flashcardFacade).save(any(FlashCard.class));
		assertThat(result, is(FlashCard.class));
	}
	
	@Test
	public void postWithNewTagByName() {
		when(tagFacade.findByName(any(String.class))).thenReturn(null);
		when(flashcardFacade.save(any(FlashCard.class))).thenReturn(flashcard);
		
		FlashCard flashcard = new FlashCard();
		flashcard.getTags().add(new Tag("tag1"));
		FlashCard result = resource.post(flashcard);
		
		verify(tagFacade).findByName(any(String.class));
		verify(flashcardFacade).save(any(FlashCard.class));
		assertThat(result, is(FlashCard.class));
	}
	
	@Test
	public void postWithTagById() {
		when(flashcardFacade.save(any(FlashCard.class))).thenReturn(flashcard);
		
		FlashCard flashcard = new FlashCard();
		flashcard.getTags().add(new Tag(1L));
		FlashCard result = resource.post(flashcard);
		
		verify(flashcardFacade).save(any(FlashCard.class));
		assertThat(result, is(FlashCard.class));
	}
}
