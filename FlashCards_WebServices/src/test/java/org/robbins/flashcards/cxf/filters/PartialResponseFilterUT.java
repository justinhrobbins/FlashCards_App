
package org.robbins.flashcards.cxf.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.model.Parameter;
import org.apache.cxf.jaxrs.model.ParameterType;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.jackson.CustomObjectMapper;
import org.robbins.flashcards.service.util.FieldInitializerUtil;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Category(UnitTest.class)
public class PartialResponseFilterUT extends BaseMockingTest {

    @Mock
    private FieldInitializerUtil mockFieldInitializer;

    @Mock
    private CustomObjectMapper mockObjectMapper;

    @Mock
    private UriInfo mockUriInfo;

    private PartialResponseFilter partialResponseFilter;

    @Before
    public void before() {
        partialResponseFilter = new PartialResponseFilter();
        ReflectionTestUtils.setField(partialResponseFilter, "fieldInitializer",
                mockFieldInitializer);
        ReflectionTestUtils.setField(partialResponseFilter, "objectMapper",
                mockObjectMapper);
        ReflectionTestUtils.setField(partialResponseFilter, "uriInfo", mockUriInfo);

    }

    @Test
    public void oriIsNull() {
        Response response = partialResponseFilter.handleResponse(null, null, null);
        assertThat(response, is(nullValue()));
    }

    @Test
    public void oriHttpMethodNotGet() {
        OperationResourceInfo mockOri = mock(OperationResourceInfo.class);

        Response response = partialResponseFilter.handleResponse(null, mockOri, null);
        assertThat(response, is(nullValue()));
    }

    @Test
    public void responseSatusNotOk() {
        OperationResourceInfo mockOri = mock(OperationResourceInfo.class);
        Response mockResponse = mock(Response.class);
        when(mockOri.getHttpMethod()).thenReturn("GET");
        when(mockResponse.getStatus()).thenReturn(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        Response response = partialResponseFilter.handleResponse(null, mockOri,
                mockResponse);
        assertThat(response, is(nullValue()));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void oriProduceTypesNotJson() {
        OperationResourceInfo mockOri = mock(OperationResourceInfo.class);
        Response mockResponse = mock(Response.class);
        when(mockOri.getHttpMethod()).thenReturn("GET");
        when(mockResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());

        List mediaTypes = mock(List.class);
        MediaType mediaType = mock(MediaType.class);
        when(mockOri.getProduceTypes()).thenReturn(mediaTypes);
        when(mediaTypes.get(0)).thenReturn(mediaType);
        when(mediaType.toString()).thenReturn(MediaType.TEXT_PLAIN);

        Response response = partialResponseFilter.handleResponse(null, mockOri,
                mockResponse);
        assertThat(response, is(nullValue()));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void blankFields() {
        OperationResourceInfo mockOri = mock(OperationResourceInfo.class);
        Response mockResponse = mock(Response.class);
        when(mockOri.getHttpMethod()).thenReturn("GET");
        when(mockResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());

        List mediaTypes = mock(List.class);
        MediaType mediaType = mock(MediaType.class);
        when(mockOri.getProduceTypes()).thenReturn(mediaTypes);
        when(mediaTypes.get(0)).thenReturn(mediaType);
        when(mediaType.toString()).thenReturn(MediaType.APPLICATION_JSON);

        when(mockResponse.getEntity()).thenReturn(new Object());
        MultivaluedMap map = mock(MultivaluedMap.class);
        when(mockUriInfo.getQueryParameters()).thenReturn(map);
        String fields = new String();
        when(map.getFirst("fields")).thenReturn(fields);

        Response response = partialResponseFilter.handleResponse(null, mockOri,
                mockResponse);
        assertThat(response, is(nullValue()));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test(expected = WebApplicationException.class)
    public void nullEntity() {
        OperationResourceInfo mockOri = mock(OperationResourceInfo.class);
        Response mockResponse = mock(Response.class);
        when(mockOri.getHttpMethod()).thenReturn("GET");
        when(mockResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());

        List mediaTypes = mock(List.class);
        MediaType mediaType = mock(MediaType.class);
        when(mockOri.getProduceTypes()).thenReturn(mediaTypes);
        when(mediaTypes.get(0)).thenReturn(mediaType);
        when(mediaType.toString()).thenReturn(MediaType.APPLICATION_JSON);

        when(mockResponse.getEntity()).thenReturn(null);
        MultivaluedMap map = mock(MultivaluedMap.class);
        when(mockUriInfo.getQueryParameters()).thenReturn(map);
        String fields = new String();
        when(map.getFirst("fields")).thenReturn(fields);

        List<Parameter> parameters = new ArrayList<Parameter>();
        Parameter parameter = new Parameter(ParameterType.QUERY, 0, "fields");
        parameter.setDefaultValue("default_value");
        parameters.add(parameter);
        when(mockOri.getParameters()).thenReturn(parameters);

        Response response = partialResponseFilter.handleResponse(null, mockOri,
                mockResponse);
        assertThat(response, is(nullValue()));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void defaultParameter() throws IOException {
        OperationResourceInfo mockOri = mock(OperationResourceInfo.class);

        String entity = new String("my_entity");
        ResponseBuilder builder = new ResponseBuilderImpl();
        Response response = builder.status(Response.Status.OK.getStatusCode()).entity(
                entity).build();

        when(mockOri.getHttpMethod()).thenReturn("GET");

        List mediaTypes = mock(List.class);
        MediaType mediaType = mock(MediaType.class);
        when(mockOri.getProduceTypes()).thenReturn(mediaTypes);
        when(mediaTypes.get(0)).thenReturn(mediaType);
        when(mediaType.toString()).thenReturn(MediaType.APPLICATION_JSON);

        MultivaluedMap map = mock(MultivaluedMap.class);
        when(mockUriInfo.getQueryParameters()).thenReturn(map);
        String fields = new String();
        when(map.getFirst("fields")).thenReturn(fields);

        List<Parameter> parameters = new ArrayList<Parameter>();
        Parameter parameter = new Parameter(ParameterType.QUERY, 0, "fields");
        parameter.setDefaultValue("default_value");
        parameters.add(parameter);
        when(mockOri.getParameters()).thenReturn(parameters);

        ObjectWriter writer = mock(ObjectWriter.class);
        when(mockObjectMapper.writer(any(SimpleFilterProvider.class))).thenReturn(writer);
        when(writer.writeValueAsString(any(Object.class))).thenReturn("String");

        Response result = partialResponseFilter.handleResponse(null, mockOri, response);
        assertThat(result, is(Response.class));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void defaultParameterCollection() throws IOException {
        OperationResourceInfo mockOri = mock(OperationResourceInfo.class);

        List<String> entity = Arrays.asList(new String[] { "my_entity" });
        ResponseBuilder builder = new ResponseBuilderImpl();
        Response response = builder.status(Response.Status.OK.getStatusCode()).entity(
                entity).build();

        when(mockOri.getHttpMethod()).thenReturn("GET");

        List mediaTypes = mock(List.class);
        MediaType mediaType = mock(MediaType.class);
        when(mockOri.getProduceTypes()).thenReturn(mediaTypes);
        when(mediaTypes.get(0)).thenReturn(mediaType);
        when(mediaType.toString()).thenReturn(MediaType.APPLICATION_JSON);

        MultivaluedMap map = mock(MultivaluedMap.class);
        when(mockUriInfo.getQueryParameters()).thenReturn(map);
        String fields = new String();
        when(map.getFirst("fields")).thenReturn(fields);

        List<Parameter> parameters = new ArrayList<Parameter>();
        Parameter parameter = new Parameter(ParameterType.QUERY, 0, "fields");
        parameter.setDefaultValue("default_value");
        parameters.add(parameter);
        when(mockOri.getParameters()).thenReturn(parameters);

        ObjectWriter writer = mock(ObjectWriter.class);
        when(mockObjectMapper.writer(any(SimpleFilterProvider.class))).thenReturn(writer);
        when(writer.writeValueAsString(any(Object.class))).thenReturn("String");

        Response result = partialResponseFilter.handleResponse(null, mockOri, response);
        assertThat(result, is(Response.class));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void defaultParameterArray() throws IOException {
        OperationResourceInfo mockOri = mock(OperationResourceInfo.class);

        String[] entity = new String[] { "my_entity" };
        ResponseBuilder builder = new ResponseBuilderImpl();
        Response response = builder.status(Response.Status.OK.getStatusCode()).entity(
                entity).build();

        when(mockOri.getHttpMethod()).thenReturn("GET");

        List mediaTypes = mock(List.class);
        MediaType mediaType = mock(MediaType.class);
        when(mockOri.getProduceTypes()).thenReturn(mediaTypes);
        when(mediaTypes.get(0)).thenReturn(mediaType);
        when(mediaType.toString()).thenReturn(MediaType.APPLICATION_JSON);

        MultivaluedMap map = mock(MultivaluedMap.class);
        when(mockUriInfo.getQueryParameters()).thenReturn(map);
        String fields = new String();
        when(map.getFirst("fields")).thenReturn(fields);

        List<Parameter> parameters = new ArrayList<Parameter>();
        Parameter parameter = new Parameter(ParameterType.QUERY, 0, "fields");
        parameter.setDefaultValue("default_value");
        parameters.add(parameter);
        when(mockOri.getParameters()).thenReturn(parameters);

        ObjectWriter writer = mock(ObjectWriter.class);
        when(mockObjectMapper.writer(any(SimpleFilterProvider.class))).thenReturn(writer);
        when(writer.writeValueAsString(any(Object.class))).thenReturn("String");

        Response result = partialResponseFilter.handleResponse(null, mockOri, response);
        assertThat(result, is(Response.class));
    }
}
