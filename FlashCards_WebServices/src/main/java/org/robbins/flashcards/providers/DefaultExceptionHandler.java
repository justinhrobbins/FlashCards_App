package org.robbins.flashcards.providers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.robbins.flashcards.jackson.CustomObjectMapper;
import org.robbins.flashcards.webservices.exceptions.WebServicesError;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
@Component
public class DefaultExceptionHandler implements ExceptionMapper<WebApplicationException> {
	
    public Response toResponse(WebApplicationException exception) {
    	String json;
    	
    	// do we already have an exception that we can use?
    	if ((String) exception.getResponse().getEntity() != null) {
    		json = (String) exception.getResponse().getEntity();
    	}
    	// handle unexpected exception
    	else {
    		String message;
    		if (exception.getMessage() != null) {
    			message = exception.getMessage();
    		} else {
    			message = exception.getClass().getName();
    		}
    		WebServicesError error = new WebServicesError(exception.getResponse().getStatus(), message, exception.getMessage());

    		// convert to Json
    		ObjectMapper mapper = new CustomObjectMapper();
    		
    		try {
    			json = mapper.writeValueAsString(error);
    		} catch (Exception ex) {
    			throw new WebApplicationException();
    		}
    	}

		// create a Response
		ResponseBuilder builder = Response.status(exception.getResponse().getStatus());
		builder.entity(json);
		builder.type(MediaType.APPLICATION_JSON);
		return builder.build();	
    }
}
