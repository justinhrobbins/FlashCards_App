
package org.robbins.flashcards.jersey.filters;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@Category(UnitTest.class)
public class AcceptFilterUT extends BaseMockingTest {

    private AcceptFilter acceptFilter;

    @Mock
    private ContainerRequestContext mockContainerRequest;

    @Before
    public void before() {
        acceptFilter = new AcceptFilter();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void filter_WithJson() throws IOException {
        final UriInfo mockUriInfo = mock(UriInfo.class);
        final MultivaluedMap<String, String> mockQueryParameters = mock(MultivaluedMap.class);
        final List<String> mockQueryparmsList = mock(List.class);
        final MultivaluedMap<String, String> mockHeaders = mock(MultivaluedMap.class);

        when(mockContainerRequest.getUriInfo()).thenReturn(mockUriInfo);
        when(mockUriInfo.getQueryParameters()).thenReturn(mockQueryParameters);
        when(mockContainerRequest.getHeaders()).thenReturn(mockHeaders);
        when(mockQueryParameters.containsKey("accept")).thenReturn(true);
        when(mockQueryParameters.get("accept")).thenReturn(mockQueryparmsList);
        when(mockQueryparmsList.contains(MediaType.APPLICATION_JSON)).thenReturn(true);

        acceptFilter.filter(mockContainerRequest);

        verify(mockUriInfo).getQueryParameters();
        verify(mockQueryParameters).containsKey("accept");
        verify(mockQueryParameters).get("accept");
        verify(mockQueryparmsList).contains(MediaType.APPLICATION_JSON);
        verify(mockContainerRequest).getHeaders();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void filter_WithXml() throws IOException {
        final UriInfo mockUriInfo = mock(UriInfo.class);
        final MultivaluedMap<String, String> mockQueryParameters = mock(MultivaluedMap.class);
        final List<String> mockQueryparmsList = mock(List.class);
        final MultivaluedMap<String, String> mockHeaders = mock(MultivaluedMap.class);

        when(mockContainerRequest.getUriInfo()).thenReturn(mockUriInfo);
        when(mockUriInfo.getQueryParameters()).thenReturn(mockQueryParameters);
        when(mockContainerRequest.getHeaders()).thenReturn(mockHeaders);
        when(mockQueryParameters.containsKey("accept")).thenReturn(true);
        when(mockQueryParameters.get("accept")).thenReturn(mockQueryparmsList);
        when(mockQueryparmsList.contains(MediaType.APPLICATION_XML)).thenReturn(true);

        acceptFilter.filter(mockContainerRequest);

        verify(mockUriInfo).getQueryParameters();
        verify(mockQueryParameters).containsKey("accept");
        verify(mockQueryParameters).get("accept");
        verify(mockQueryparmsList).contains(MediaType.APPLICATION_XML);
        verify(mockContainerRequest).getHeaders();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = GenericWebServiceException.class)
    public void filter_WithText() throws IOException {
        final UriInfo mockUriInfo = mock(UriInfo.class);
        final  MultivaluedMap<String, String> mockQueryParameters = mock(MultivaluedMap.class);
        final List<String> mockQueryparmsList = mock(List.class);
        final MultivaluedMap<String, String> mockHeaders = mock(MultivaluedMap.class);

        when(mockContainerRequest.getUriInfo()).thenReturn(mockUriInfo);
        when(mockUriInfo.getQueryParameters()).thenReturn(mockQueryParameters);
        when(mockContainerRequest.getHeaders()).thenReturn(mockHeaders);
        when(mockQueryParameters.containsKey("accept")).thenReturn(true);
        when(mockQueryParameters.get("accept")).thenReturn(mockQueryparmsList);
        when(mockQueryparmsList.contains(MediaType.TEXT_PLAIN)).thenReturn(true);

        acceptFilter.filter(mockContainerRequest);
    }
}
