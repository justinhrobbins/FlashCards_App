package org.robbins.flashcards.service.springdata.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	
	@Before
	public void before() {
		tagService = new TagServiceImpl();
		ReflectionTestUtils.setField(tagService, "repository", repository);		
	}

	@Test
	public void findOne() {
		when(repository.findOne(Mockito.anyLong())).thenReturn(tag);
		
		Tag result = tagService.findOne(1L);
		
		verify(repository, Mockito.times(1)).findOne(1L);
		assertThat(result, is(Tag.class));
	}
}