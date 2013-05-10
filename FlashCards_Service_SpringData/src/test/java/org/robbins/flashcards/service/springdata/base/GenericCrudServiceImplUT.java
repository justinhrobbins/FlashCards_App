package org.robbins.flashcards.service.springdata.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class GenericCrudServiceImplUT extends BaseMockingTest {

	@Mock
	TagRepository repository;
	
	TagServiceImpl tagService;
	
	@Mock
	Tag tag;
	
	@Mock
	List<Tag> tags;
	
	@Before
	public void before() {
		tagService = new TagServiceImpl();
		ReflectionTestUtils.setField(tagService, "repository", repository);		
	}

	@Test
	public void save() {
		when(repository.save(tag)).thenReturn(tag);
		
		Tag result = tagService.save(tag);
		
		verify(repository, Mockito.times(1)).save(tag);
		assertThat(result, is(Tag.class));		
	}
	
	@Test
	public void findOne() {
		when(repository.findOne(Mockito.anyLong())).thenReturn(tag);
		
		Tag result = tagService.findOne(1L);
		
		verify(repository, Mockito.times(1)).findOne(1L);
		assertThat(result, is(Tag.class));
	}

	@Test
	public void exists() {
		when(repository.exists(Mockito.anyLong())).thenReturn(Boolean.TRUE);
		
		Boolean result = tagService.exists(1L);
		
		verify(repository, Mockito.times(1)).exists(1L);
		assertThat(result, is(Boolean.class));
		assertTrue(result.booleanValue());
	}
	
	@Test
	public void findAllIterable() {
		List<Long> ids = new ArrayList<Long>();
		when(repository.findAll(ids)).thenReturn(tags);
		
		Iterable<Tag> results = tagService.findAll(ids);
		
		verify(repository, Mockito.times(1)).findAll(ids);
		assertThat(results, is(List.class));
	}
}