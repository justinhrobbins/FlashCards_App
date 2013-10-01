package org.robbins.flashcards.webservices.cxf.providers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.providers.DefaultExceptionHandler;
import org.robbins.flashcards.webservices.exceptions.WebServicesError;
import org.robbins.tests.UnitTest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse.Status;

@Category(UnitTest.class)
public class DefaultExceptionHandlerUT {

	@Test
	public void toResponse_withUnexpectedException() throws JsonParseException, JsonMappingException, IOException {
		DefaultExceptionHandler handler = new DefaultExceptionHandler();
		WebApplicationException webApplicationException = new WebApplicationException();
		
		Response result = handler.toResponse(webApplicationException);

		ObjectMapper mapper = new ObjectMapper();
		WebServicesError webServicesError = mapper.readValue((String) result.getEntity(), WebServicesError.class);
		
		assertThat(webServicesError, is(WebServicesError.class));
		assertThat(webServicesError.getErrorId(), is(Status.INTERNAL_SERVER_ERROR.getStatusCode()));
	}

	@Test
	public void toResponse_withUnexpectedExceptionAndMessage() throws JsonParseException, JsonMappingException, IOException {
		DefaultExceptionHandler handler = new DefaultExceptionHandler();
		
		Throwable throwable = new Throwable("error_message"); 
		WebApplicationException webApplicationException = new WebApplicationException(throwable);
		
		Response result = handler.toResponse(webApplicationException);

		ObjectMapper mapper = new ObjectMapper();
		WebServicesError webServicesError = mapper.readValue((String) result.getEntity(), WebServicesError.class);
		
		assertThat(webServicesError, is(WebServicesError.class));
		assertThat(webServicesError.getErrorId(), is(Status.INTERNAL_SERVER_ERROR.getStatusCode()));
	}
	
	@Test
	public void toResponse_withExpectedException() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		DefaultExceptionHandler handler = new DefaultExceptionHandler();
		WebServicesError entity = new WebServicesError(Status.NOT_FOUND.getStatusCode(), Status.NOT_FOUND.getReasonPhrase(), Status.NOT_FOUND.getFamily().toString());
		String json = mapper.writeValueAsString(entity);

		ResponseBuilder builder = new ResponseBuilderImpl();
		Response response = builder.status(Response.Status.OK.getStatusCode()).entity(json).build();
		WebApplicationException webApplicationException = new WebApplicationException(response);

		Response result = handler.toResponse(webApplicationException);


		WebServicesError webServicesError = mapper.readValue((String) result.getEntity(), WebServicesError.class);
		
		assertThat(webServicesError, is(WebServicesError.class));
		assertThat(webServicesError.getErrorId(), is(Status.NOT_FOUND.getStatusCode()));
	}
}
