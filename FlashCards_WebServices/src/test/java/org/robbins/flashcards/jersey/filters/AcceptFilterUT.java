package org.robbins.flashcards.jersey.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;

import com.sun.jersey.spi.container.ContainerRequest;

@Category(UnitTest.class)
public class AcceptFilterUT extends BaseMockingTest {

	private AcceptFilter acceptFilter;
	
	@Mock
	ContainerRequest mockContainerRequest;
	
	@Before
	public void before() {
		acceptFilter = new AcceptFilter();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void filter_WithJson() {
		MultivaluedMap<String, String> mockQueryParameters = mock(MultivaluedMap.class);
		List<String> mockQueryparmsList = mock(List.class);
		MultivaluedMap<String, String> mockHeaders = mock(MultivaluedMap.class);
		
		when(mockContainerRequest.getQueryParameters()).thenReturn(mockQueryParameters);
		when(mockContainerRequest.getRequestHeaders()).thenReturn(mockHeaders);
		when(mockQueryParameters.containsKey("accept")).thenReturn(true);
		when(mockQueryParameters.get("accept")).thenReturn(mockQueryparmsList);
		when(mockQueryparmsList.contains(MediaType.APPLICATION_JSON)).thenReturn(true);
		
		ContainerRequest result = acceptFilter.filter(mockContainerRequest);
		
		verify(mockContainerRequest).getQueryParameters();
		verify(mockQueryParameters).containsKey("accept");
		verify(mockQueryParameters).get("accept");
		verify(mockQueryparmsList).contains(MediaType.APPLICATION_JSON);
		verify(mockContainerRequest).getRequestHeaders();
		assertThat(result, is(ContainerRequest.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void filter_WithXml() {
		MultivaluedMap<String, String> mockQueryParameters = mock(MultivaluedMap.class);
		List<String> mockQueryparmsList = mock(List.class);
		MultivaluedMap<String, String> mockHeaders = mock(MultivaluedMap.class);
		
		when(mockContainerRequest.getQueryParameters()).thenReturn(mockQueryParameters);
		when(mockContainerRequest.getRequestHeaders()).thenReturn(mockHeaders);
		when(mockQueryParameters.containsKey("accept")).thenReturn(true);
		when(mockQueryParameters.get("accept")).thenReturn(mockQueryparmsList);
		when(mockQueryparmsList.contains(MediaType.APPLICATION_XML)).thenReturn(true);
		
		ContainerRequest result = acceptFilter.filter(mockContainerRequest);
		
		verify(mockContainerRequest).getQueryParameters();
		verify(mockQueryParameters).containsKey("accept");
		verify(mockQueryParameters).get("accept");
		verify(mockQueryparmsList).contains(MediaType.APPLICATION_XML);
		verify(mockContainerRequest).getRequestHeaders();
		assertThat(result, is(ContainerRequest.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = GenericWebServiceException.class)
	public void filter_WithText() {
		MultivaluedMap<String, String> mockQueryParameters = mock(MultivaluedMap.class);
		List<String> mockQueryparmsList = mock(List.class);
		MultivaluedMap<String, String> mockHeaders = mock(MultivaluedMap.class);
		
		when(mockContainerRequest.getQueryParameters()).thenReturn(mockQueryParameters);
		when(mockContainerRequest.getRequestHeaders()).thenReturn(mockHeaders);
		when(mockQueryParameters.containsKey("accept")).thenReturn(true);
		when(mockQueryParameters.get("accept")).thenReturn(mockQueryparmsList);
		when(mockQueryparmsList.contains(MediaType.TEXT_PLAIN)).thenReturn(true);
		
		acceptFilter.filter(mockContainerRequest);
	}
}
