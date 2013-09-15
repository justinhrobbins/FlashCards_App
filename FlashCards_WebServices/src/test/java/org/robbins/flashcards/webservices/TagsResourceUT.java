package org.robbins.flashcards.webservices;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.Tag;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class TagsResourceUT extends BaseMockingTest {

	@Mock
	TagFacade tagFacade;
	
	@Mock
	Tag tag;
	
	TagsResource resource;
	
	@Before
	public void before() {
		resource = new TagsResource();
		ReflectionTestUtils.setField(resource, "tagFacade", tagFacade);
	}
	
	@Test
	public void search() {
		when(tagFacade.findByName(any(String.class))).thenReturn(tag);
		
		Tag result = resource.searchByName(any(String.class));
		
		verify(tagFacade).findByName(any(String.class));
		assertThat(result, is(Tag.class));
	}
	
	@Test
	public void put() {
		when(tagFacade.findOne(any(Long.class))).thenReturn(tag);
		when(tagFacade.save(any(Tag.class))).thenReturn(tag);
		
		Response response = resource.put(1L, tag);
		
		verify(tagFacade).findOne(any(Long.class));
		verify(tagFacade).save(any(Tag.class));
		assertThat(response.getStatus(), is(204));
	}
}
