package org.robbins.flashcards.service.springdata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.springdata.TagRepository;
import org.robbins.tests.BaseTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class TagServiceUT extends BaseTest {

	@Mock TagRepository repository;
	TagServiceImpl tagService;
	
	@Before
	public void before() {
		tagService = new TagServiceImpl();
		ReflectionTestUtils.setField(tagService, "repository", repository);		
	}
	
	@Test
	public void testFindByName() {
		when(repository.findByName(Mockito.anyString())).thenReturn(new Tag("EJB"));
		
		Tag tag = tagService.findByName("EJB");
		
		Mockito.verify(repository, Mockito.times(1)).findByName("EJB");
		assertThat(tag, is(Tag.class));
	}
}