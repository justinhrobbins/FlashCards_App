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
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class TagsResourceUT extends BaseMockingTest {

	@Mock
	TagService service;
	
	@Mock
	Tag tag;
	
	TagsResource resource;
	
	@Before
	public void before() {
		resource = new TagsResource();
		ReflectionTestUtils.setField(resource, "service", service);
	}
	
	@Test
	public void search() {
		when(service.findByName(any(String.class))).thenReturn(tag);
		
		Tag result = resource.searchByName(any(String.class));
		
		verify(service).findByName(any(String.class));
		assertThat(result, is(Tag.class));
	}
	
	@Test
	public void put() {
		when(service.findOne(any(Long.class))).thenReturn(tag);
		when(service.save(any(Tag.class))).thenReturn(tag);
		
		Response response = resource.put(1L, tag);
		
		verify(service).findOne(any(Long.class));
		verify(service).save(any(Tag.class));
		assertThat(response.getStatus(), is(204));
	}
}
