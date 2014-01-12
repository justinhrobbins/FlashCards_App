package org.robbins.flashcards.webservices.base;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.webservices.TagsResource;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.util.ReflectionTestUtils;

import com.sun.jersey.api.JResponse;

@Category(UnitTest.class)
public class AbstractGenericResourceUT extends BaseMockingTest {

	@Mock
	private TagFacade mockTagFacade;
	
	@Mock
	private TagDto mockTagDto;
	
	private List<TagDto> tagDtoList;
	
	private TagsResource resource;
	
	@Before
	public void before() {
		resource = new TagsResource();
		tagDtoList = Arrays.asList(mockTagDto);
		
		ReflectionTestUtils.setField(resource, "tagFacade", mockTagFacade);
	}

	@Test
	public void list() throws ServiceException {
		when(mockTagFacade.list(null, null, null, null, null)).thenReturn(tagDtoList);
		
		JResponse<List<TagDto>> results = resource.list(null, null, null, null, null);
		
		verify(mockTagFacade).list(null, null, null, null, null);
		assertThat(results.getEntity(), is(List.class));
	}

	@Test(expected =GenericWebServiceException.class)
	public void list_NullResult() throws ServiceException {
		when(mockTagFacade.list(null, null, null, null)).thenReturn(null);
		
		resource.list(null, null, null, null, null);
	}
	
	@Test(expected = WebApplicationException.class)
	public void listWithInvalidSortParameter() throws ServiceException {
		when(mockTagFacade.list(null, null, "bad_parameter", "asc", null)).thenThrow(new InvalidDataAccessApiUsageException("error"));
		
		resource.list(null, null, "bad_parameter", "asc", null);
	}
	
	@Test
	public void listWithSort() throws ServiceException {
		when(mockTagFacade.list(null, null, "name", "asc", null)).thenReturn(tagDtoList);
		
		JResponse<List<TagDto>> results = resource.list(null, null, "name", "asc", null);
		
		verify(mockTagFacade).list(null, null, "name", "asc", null);
		assertThat(results.getEntity(), is(List.class));
	}
	
	@Test
	public void listWithPagingAndSort() throws ServiceException {
		when(mockTagFacade.list(0, 1, "name", "desc", null)).thenReturn(tagDtoList);
		
		JResponse<List<TagDto>> results = resource.list(0, 1, "name", "desc", null);
		
		verify(mockTagFacade).list(0, 1, "name", "desc", null);
		assertThat(results.getEntity(), is(List.class));
	}

	@Test
	public void listWithPagingNoSort() throws ServiceException {
		when(mockTagFacade.list(0, 1, null, null, null)).thenReturn(tagDtoList);
		
		JResponse<List<TagDto>> results = resource.list(0, 1, null, null, null);
		
		verify(mockTagFacade).list(0, 1, null, null, null);
		assertThat(results.getEntity(), is(List.class));
	}
	
	@Test
	public void count() {
		when(mockTagFacade.count()).thenReturn(1L);
		
		Long result = resource.count();
		
		verify(mockTagFacade).count();
		assertThat(result, is(Long.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findOne() throws ServiceException {
		when(mockTagFacade.findOne(anyLong(), anySet())).thenReturn(new TagDto(1L));
		
		TagDto result = resource.findOne(1L, null);
		
		verify(mockTagFacade).findOne(anyLong(), anySet());
		assertThat(result, is(TagDto.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findOne_WithFields() throws ServiceException {
		String fields = "name,flashcards,userpassword";
		when(mockTagFacade.findOne(anyLong(), anySet())).thenReturn(new TagDto(1L));
		
		TagDto result = resource.findOne(1L, fields);
		
		verify(mockTagFacade).findOne(anyLong(), anySet());
		assertThat(result, is(TagDto.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = GenericWebServiceException.class)
	public void findOne_ReturnsNull() throws ServiceException {
		when(mockTagFacade.findOne(anyLong(), anySet())).thenReturn(null);
		
		resource.findOne(1L, null);
	}
	
	@Test
	public void post() throws ServiceException {
		when(mockTagFacade.save(any(TagDto.class))).thenReturn(new TagDto(1L));
		
		TagDto result = resource.post(new TagDto());
		
		verify(mockTagFacade).save(any(TagDto.class));
		assertThat(result, is(TagDto.class));
	}
	
	@Test 
	public void delete() {
		Response response = resource.delete(anyLong());
		
		verify(mockTagFacade).delete(anyLong());
		assertThat(response.getStatus(), is(204));		
	}
	
	@Test 
	public void update() throws ServiceException {
		when(mockTagFacade.findOne(any(Long.class))).thenReturn(mockTagDto);
		when(mockTagFacade.save(any(TagDto.class))).thenReturn(mockTagDto);
		
		Response response = resource.update(1L, mockTagDto);
		
		verify(mockTagFacade).findOne(any(Long.class));
		verify(mockTagFacade).save(any(TagDto.class));
		assertThat(response.getStatus(), is(204));		
	}
}
