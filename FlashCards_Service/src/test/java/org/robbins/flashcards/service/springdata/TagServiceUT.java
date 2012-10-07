package org.robbins.flashcards.service.springdata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.springdata.TagRepository;
import org.robbins.flashcards.service.springdata.TagServiceImpl;
import org.springframework.test.util.ReflectionTestUtils;

public class TagServiceUT extends BaseTestCase {

	@Mock TagRepository repository;
	TagServiceImpl tagService;
	
	@Before
	public void before() {
		tagService = new TagServiceImpl();
		ReflectionTestUtils.setField(tagService, "repository", repository);		
	}
	
	@Test
	public void testFindByName() {
		when(repository.findByName("EJB")).thenReturn(new Tag("EJB"));
		
		Tag tag = tagService.findByName("EJB");
		
		Mockito.verify(repository, Mockito.times(1)).findByName("EJB");
		assertThat(tag, is(Tag.class));
	}
}