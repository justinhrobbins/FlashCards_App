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
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.FlashCardService;
import org.robbins.flashcards.service.TagService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FlashCardsResourceUT extends BaseMockingTest {

	@Mock
	FlashCardService flashCardService;

	@Mock
	TagService tagService;
	
	@Mock
	FlashCard flashcard;
	
	FlashCardsResource resource;
	
	@Before
	public void before() {
		resource = new FlashCardsResource();
		ReflectionTestUtils.setField(resource, "flashcardService", flashCardService);
		ReflectionTestUtils.setField(resource, "tagService", tagService);
	}

	@Test
	public void searchByQuestionWithPaging() {
		when(flashCardService.findByQuestionLike(any(String.class), any(PageRequest.class))).thenReturn(new ArrayList<FlashCard>());
		
		FlashCard[] results = resource.search(0, 1, "Question", null);
		
		verify(flashCardService).findByQuestionLike(any(String.class), any(PageRequest.class));
		assertThat(results, is(FlashCard[].class));
	}
	
	@Test
	public void searchByQuestionNoPaging() {
		when(flashCardService.findByQuestionLike(any(String.class))).thenReturn(new ArrayList<FlashCard>());
		
		FlashCard[] results = resource.search(null, null, "Question", null);
		
		verify(flashCardService).findByQuestionLike(any(String.class));
		assertThat(results, is(FlashCard[].class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void searchByTagsWithPaging() {
		when(flashCardService.findByTagsIn(any(Set.class), any(PageRequest.class))).thenReturn(new ArrayList<FlashCard>());
		
		FlashCard[] results = resource.search(0, 1, null, "1,2");
		
		verify(flashCardService).findByTagsIn(any(Set.class), any(PageRequest.class));
		assertThat(results, is(FlashCard[].class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void searchByTagsNoPaging() {
		when(flashCardService.findByTagsIn(any(Set.class))).thenReturn(new ArrayList<FlashCard>());
		
		FlashCard[] results = resource.search(null, null, null, "1,2");
		
		verify(flashCardService).findByTagsIn(any(Set.class));
		assertThat(results, is(FlashCard[].class));
	}

	@Test(expected = WebApplicationException.class)
	public void searchWithBadRequest() {
		resource.search(null, null, null, null);
	}
	
	@Test
	public void searchCount() {
		when(flashCardService.findByQuestionLike(any(String.class))).thenReturn(new ArrayList<FlashCard>());
		
		Long results = resource.searchCount("Question", null);
		
		verify(flashCardService).findByQuestionLike(any(String.class));
		assertThat(results, is(Long.class));
	}
	
	@Test
	public void put() {
		when(flashCardService.findOne(any(Long.class))).thenReturn(flashcard);
		when(flashCardService.save(any(FlashCard.class))).thenReturn(flashcard);
		
		Response response = resource.put(1L, flashcard);
		
		verify(flashCardService).findOne(any(Long.class));
		verify(flashCardService).save(any(FlashCard.class));
		assertThat(response.getStatus(), is(204));
	}
	
	@Test
	public void postNoTags() {
		when(flashCardService.save(any(FlashCard.class))).thenReturn(flashcard);
		
		FlashCard result = resource.post(flashcard);
		
		verify(flashCardService).save(any(FlashCard.class));
		assertThat(result, is(FlashCard.class));
	}
	
	@Test
	public void postWithTagByName() {
		when(tagService.findByName(any(String.class))).thenReturn(new Tag());
		when(flashCardService.save(any(FlashCard.class))).thenReturn(flashcard);
		
		FlashCard flashcard = new FlashCard();
		flashcard.getTags().add(new Tag("tag1"));
		FlashCard result = resource.post(flashcard);
		
		verify(tagService).findByName(any(String.class));
		verify(flashCardService).save(any(FlashCard.class));
		assertThat(result, is(FlashCard.class));
	}
	
	@Test
	public void postWithNewTagByName() {
		when(tagService.findByName(any(String.class))).thenReturn(null);
		when(flashCardService.save(any(FlashCard.class))).thenReturn(flashcard);
		
		FlashCard flashcard = new FlashCard();
		flashcard.getTags().add(new Tag("tag1"));
		FlashCard result = resource.post(flashcard);
		
		verify(tagService).findByName(any(String.class));
		verify(flashCardService).save(any(FlashCard.class));
		assertThat(result, is(FlashCard.class));
	}
	
	@Test
	public void postWithTagById() {
		when(flashCardService.save(any(FlashCard.class))).thenReturn(flashcard);
		
		FlashCard flashcard = new FlashCard();
		flashcard.getTags().add(new Tag(1L));
		FlashCard result = resource.post(flashcard);
		
		verify(flashCardService).save(any(FlashCard.class));
		assertThat(result, is(FlashCard.class));
	}
}
