package org.robbins.flashcards.repository.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.jpa.FlashCardRepository;
import org.robbins.flashcards.repository.jpa.FlashCardRepositoryImpl;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FlashCardRepositoryImplUT extends BaseMockingTest {

	@Mock EntityManager em;
	Query query;
	List<FlashCard> results;
	FlashCardRepository repository;
	
	@Before
	public void before() {
		repository = new FlashCardRepositoryImpl();
		query = Mockito.mock(Query.class);
		results = new ArrayList<FlashCard>();
		results.add(new FlashCard());
		ReflectionTestUtils.setField(repository, "em", em);
		
		when(em.createQuery(Mockito.anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(results);
	}
	
	@Test
	public void testFindByQuestionLike() {
		List<FlashCard> results = repository.findByQuestionLike("question"); 
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(results, is(List.class));
	}

	@Test
	public void testFindByTagsIn() {
		Set<Tag> tags = new HashSet<Tag>();
		tags.add(new Tag("tag name"));
		List<FlashCard> results = repository.findByTagsIn(tags);
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(results, is(List.class));
	}

	@Test
	public void testFindByTagsInPageable() {
		Pageable pageRequest = new PageRequest(1, 1);
		List<FlashCard> results = repository.findByTagsIn(new HashSet<Tag>(), pageRequest);
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(results, is(List.class));
	}
	
	@Test
	public void testFindByQuestion() {
		FlashCard result = repository.findByQuestion("question"); 
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(result, is(FlashCard.class));
	}
	
	@Test
	public void testFindByQuestion_NoResults() {
		List<FlashCard> results = new ArrayList<FlashCard>();
		when(query.getResultList()).thenReturn(results);
		
		FlashCard flashCard = repository.findByQuestion("question"); 
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(flashCard, is(nullValue()));
	}
	
	@Test
	public void testFindAll() {
		List<FlashCard> results = repository.findAll();
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(results, is(List.class));
	}
	
	@Test
	public void testfindAllPageable() {
		Pageable pageRequest = new PageRequest(1, 1);
		Page<FlashCard> results = repository.findAll(pageRequest);
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(results, is(Page.class));		
	}
	
	@Test
	public void testfindAllSort() {
		Sort sort = new Sort("name");
		List<FlashCard> results = repository.findAll(sort);
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(results, is(List.class));		
	}
	
	@Test
	public void testCount() {
		when(query.getSingleResult()).thenReturn(new Long(2));
		
		Long count = repository.count();
		
		Mockito.verify(query, Mockito.times(1)).getSingleResult();
		assertThat(count, is(Long.class));		
	}
}