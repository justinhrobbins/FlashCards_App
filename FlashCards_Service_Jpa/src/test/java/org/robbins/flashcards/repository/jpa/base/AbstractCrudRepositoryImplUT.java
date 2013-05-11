package org.robbins.flashcards.repository.jpa.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.auditing.AuditingAwareUser;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.jpa.TagRepository;
import org.robbins.flashcards.repository.jpa.TagRepositoryImpl;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class AbstractCrudRepositoryImplUT extends BaseMockingTest {

	@Mock
	EntityManager em;
	
	@Mock
	Tag tag;
	
	@Mock
	AuditingAwareUser auditorAware;
	
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
		ReflectionTestUtils.setField(repository, "auditorAware", auditorAware);
		
		when(em.createQuery(Mockito.anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(results);
	}
	
	@Test
	public void save() {
		Tag result = repository.save(tag); 
		
		Mockito.verify(em, Mockito.times(1)).persist(tag);
		assertThat(result, is(Tag.class));
	}
	
	@Test
	public void update() {
		Tag tag = new Tag(1L);
		Tag result = repository.save(tag); 
		
		Mockito.verify(em, Mockito.times(1)).merge(tag);
		assertThat(result, is(Tag.class));
	}
	
	@Test
	public void findOne() {
		Long id = 1L;
		Tag result = Mockito.mock(Tag.class);
		when(em.find(Tag.class, id)).thenReturn(result);
		
		result = repository.findOne(id);
		
		Mockito.verify(em, Mockito.times(1)).find(Tag.class, id);
		assertThat(result, is(Tag.class));
	}
	
	@Test
	public void deleteByEntity() {
		repository.delete(tag); 
		
		Mockito.verify(em, Mockito.times(1)).remove(tag);
	}
	
	@Test
	public void deleteById() {
		Long id = 1L;
		Tag result = Mockito.mock(Tag.class);
		when(em.find(Tag.class, id)).thenReturn(result);
		
		repository.delete(id); 
		
		Mockito.verify(em, Mockito.times(1)).remove(result);
	}
}