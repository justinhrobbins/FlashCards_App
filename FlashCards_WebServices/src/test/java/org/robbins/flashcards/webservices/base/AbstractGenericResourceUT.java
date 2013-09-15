package org.robbins.flashcards.webservices.base;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.webservices.TagsResource;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class AbstractGenericResourceUT extends BaseMockingTest {

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
	public void list() {
		when(tagFacade.findAll()).thenReturn(new ArrayList<Tag>());
		
		List<Tag> results = resource.list(null, null, null, null);
		
		verify(tagFacade).findAll();
		assertThat(results, is(List.class));
	}

	@Test(expected = WebApplicationException.class)
	public void listWithInvalidSortParameter() {
		when(tagFacade.findAll(any(Sort.class))).thenThrow(new InvalidDataAccessApiUsageException("error"));
		
		resource.list(null, null, "bad_parameter", "asc");
	}
	
	@Test
	public void listWithSort() {
		when(tagFacade.findAll(any(Sort.class))).thenReturn(new ArrayList<Tag>());
		
		List<Tag> results = resource.list(null, null, "name", "asc");
		
		verify(tagFacade).findAll(any(Sort.class));
		assertThat(results, is(List.class));
	}
	
	@Test
	public void listWithPagingAndSort() {
		when(tagFacade.findAll()).thenReturn(new ArrayList<Tag>());
		
		List<Tag> results = resource.list(0, 1, "name", "desc");
		
		verify(tagFacade).findAll(any(PageRequest.class));
		assertThat(results, is(List.class));
	}

	@Test
	public void listWithPagingNoSort() {
		when(tagFacade.findAll()).thenReturn(new ArrayList<Tag>());
		
		List<Tag> results = resource.list(0, 1, null, null);
		
		verify(tagFacade).findAll(any(PageRequest.class));
		assertThat(results, is(List.class));
	}
	
	@Test
	public void count() {
		when(tagFacade.count()).thenReturn(1L);
		
		Long result = resource.count();
		
		verify(tagFacade).count();
		assertThat(result, is(Long.class));
	}

	@Test
	public void findOne() {
		when(tagFacade.findOne(anyLong())).thenReturn(new Tag(1L));
		
		Tag result = resource.findOne(1L);
		
		verify(tagFacade).findOne(anyLong());
		assertThat(result, is(Tag.class));
	}
	
	@Test
	public void post() {
		when(tagFacade.save(any(Tag.class))).thenReturn(new Tag(1L));
		
		Tag result = resource.post(new Tag());
		
		verify(tagFacade).save(any(Tag.class));
		assertThat(result, is(Tag.class));
	}
	
	@Test 
	public void delete() {
		Response response = resource.delete(anyLong());
		
		verify(tagFacade).delete(anyLong());
		assertThat(response.getStatus(), is(204));		
	}
	
	@Test 
	public void update() {
		when(tagFacade.findOne(any(Long.class))).thenReturn(tag);
		when(tagFacade.save(any(Tag.class))).thenReturn(tag);
		
		Response response = resource.update(1L, tag);
		
		verify(tagFacade).findOne(any(Long.class));
		verify(tagFacade).save(any(Tag.class));
		assertThat(response.getStatus(), is(204));		
	}
}
