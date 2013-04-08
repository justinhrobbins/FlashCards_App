package org.robbins.flashcards.service.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.jpa.TagRepository;
import org.robbins.flashcards.service.jpa.TagServiceImpl;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class TagServiceUT extends BaseMockingTest {

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