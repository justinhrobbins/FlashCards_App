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
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class TagsResourceUT extends BaseMockingTest {

	@Mock
	TagFacade tagFacade;
	
	@Mock
	TagDto tagDto;
	
	TagsResource resource;
	
	@Before
	public void before() {
		resource = new TagsResource();
		ReflectionTestUtils.setField(resource, "tagFacade", tagFacade);
	}
	
	@Test
	public void search() {
		when(tagFacade.findByName(any(String.class))).thenReturn(tagDto);
		
		TagDto result = resource.searchByName(any(String.class));
		
		verify(tagFacade).findByName(any(String.class));
		assertThat(result, is(TagDto.class));
	}
	
	@Test
	public void put() throws ServiceException {
		when(tagFacade.findOne(any(Long.class))).thenReturn(tagDto);
		when(tagFacade.save(any(TagDto.class))).thenReturn(tagDto);
		
		Response response = resource.put(1L, tagDto);
		
		verify(tagFacade).findOne(any(Long.class));
		verify(tagFacade).save(any(TagDto.class));
		assertThat(response.getStatus(), is(204));
	}
}
