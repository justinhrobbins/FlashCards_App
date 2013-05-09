package org.robbins.flashcards.service.springdata.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.springdata.TagRepository;
import org.robbins.flashcards.service.springdata.TagServiceImpl;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class GenericJpaServiceImplUT extends BaseMockingTest {

	@Mock
	TagRepository repository;
	
	TagServiceImpl service;
	
	@Mock
	Tag tag;
	
	@Mock
	List<Tag> tags;
	
	@Before
	public void before() {
		service = new TagServiceImpl();
		ReflectionTestUtils.setField(service, "repository", repository);		
	}
	
	@Test
	public void findAll() {
		when(repository.findAll()).thenReturn(Arrays.asList(tag));
		
		List<Tag> results = service.findAll();
		
		verify(repository, Mockito.times(1)).findAll();
		assertThat(results, is(List.class));
	}
	
	@Test
	public void findAllSorted() {
		Sort sort = new Sort("name");
		when(repository.findAll(sort)).thenReturn(Arrays.asList(tag));
		
		List<Tag> results = service.findAll(sort);
		
		verify(repository, Mockito.times(1)).findAll(sort);
		assertThat(results, is(List.class));
	}
	
	@Test
	public void saveEntities() {
		when(repository.save(tags)).thenReturn(tags);
		
		List<Tag> results = service.save(tags);
		
		verify(repository, Mockito.times(1)).save(tags);
		assertThat(results, is(List.class));
	}
	
	@Test
	public void flush() {
		service.flush();
		
		verify(repository, Mockito.times(1)).flush();
	}
	
	@Test
	public void saveAndFlush() {
		when(repository.saveAndFlush(tag)).thenReturn(tag);
		
		Tag result = service.saveAndFlush(tag);
		
		verify(repository, Mockito.times(1)).saveAndFlush(tag);
		assertThat(result, is(Tag.class));
	}
	
	@Test
	public void deleteInBatch() {
		service.deleteInBatch(tags);
		
		verify(repository, Mockito.times(1)).deleteInBatch(tags);
	}
	
	@Test
	public void deleteAllInBatch() {
		service.deleteAllInBatch();
		
		verify(repository, Mockito.times(1)).deleteAllInBatch();
	}
}