package org.robbins.flashcards.webservices.base;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagService;
import org.robbins.flashcards.webservices.TagsResource;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class AbstractGenericResourceUT extends BaseMockingTest {

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
	public void list() {
		when(service.findAll()).thenReturn(new ArrayList<Tag>());
		
		List<Tag> results = resource.list(null, null, null, null);
		
		verify(service).findAll();
		assertThat(results, is(List.class));
	}

	@Test
	public void listWithSort() {
		when(service.findAll()).thenReturn(new ArrayList<Tag>());
		
		List<Tag> results = resource.list(null, null, "name", "asc");
		
		verify(service).findAll(any(Sort.class));
		assertThat(results, is(List.class));
	}
	
	@Test
	public void listWithPagingAndSort() {
		when(service.findAll()).thenReturn(new ArrayList<Tag>());
		
		List<Tag> results = resource.list(0, 1, "name", "desc");
		
		verify(service).findAll(any(PageRequest.class));
		assertThat(results, is(List.class));
	}

	@Test
	public void listWithPagingNoSort() {
		when(service.findAll()).thenReturn(new ArrayList<Tag>());
		
		List<Tag> results = resource.list(0, 1, null, null);
		
		verify(service).findAll(any(PageRequest.class));
		assertThat(results, is(List.class));
	}
	
	@Test
	public void count() {
		when(service.count()).thenReturn(1L);
		
		Long result = resource.count();
		
		verify(service).count();
		assertThat(result, is(Long.class));
	}

	@Test
	public void findOne() {
		when(service.findOne(anyLong())).thenReturn(new Tag(1L));
		
		Tag result = resource.findOne(1L);
		
		verify(service).findOne(anyLong());
		assertThat(result, is(Tag.class));
	}
	
	@Test
	public void post() {
		when(service.save(any(Tag.class))).thenReturn(new Tag(1L));
		
		Tag result = resource.post(new Tag());
		
		verify(service).save(any(Tag.class));
		assertThat(result, is(Tag.class));
	}
	
	@Test 
	public void delete() {
		Response response = resource.delete(anyLong());
		
		verify(service).delete(anyLong());
		assertThat(response.getStatus(), is(204));		
	}
	
	@Test 
	public void update() {
		when(service.findOne(any(Long.class))).thenReturn(tag);
		when(service.save(any(Tag.class))).thenReturn(tag);
		
		Response response = resource.update(1L, tag);
		
		verify(service).findOne(any(Long.class));
		verify(service).save(any(Tag.class));
		assertThat(response.getStatus(), is(204));		
	}
}
