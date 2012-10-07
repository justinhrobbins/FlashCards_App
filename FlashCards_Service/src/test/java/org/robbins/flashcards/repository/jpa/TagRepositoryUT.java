package org.robbins.flashcards.repository.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.BaseTestCase;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.jpa.TagRepository;
import org.robbins.flashcards.repository.jpa.TagRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

public class TagRepositoryUT extends BaseTestCase {

	@Mock EntityManager em;
	Query query;
	List<Tag> results;
	TagRepository repository;
	
	@Before
	public void before() {
		repository = new TagRepositoryImpl();
		query = Mockito.mock(Query.class);
		results = new ArrayList<Tag>();
		results.add(new Tag());
		ReflectionTestUtils.setField(repository, "em", em);
		
		when(em.createQuery(Mockito.anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(results);
	}
	
	@Test
	public void testFindByName() {
		Tag tag = repository.findByName("EJB"); 
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(tag, is(Tag.class));
	}
	
	@Test
	public void testFindAll() {
		List<Tag> tags = repository.findAll();
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(tags, is(List.class));
	}
	
	@Test
	public void testfindAllPageable() {
		Pageable pageRequest = new PageRequest(1, 1);
		Page<Tag> results = repository.findAll(pageRequest);
		
		Mockito.verify(query, Mockito.times(1)).getResultList();
		assertThat(results, is(Page.class));		
	}
	
	@Test
	public void testfindAllSort() {
		Sort sort = new Sort("name");
		List<Tag> results = repository.findAll(sort);
		
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