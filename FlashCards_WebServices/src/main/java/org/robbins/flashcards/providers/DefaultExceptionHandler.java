package org.robbins.flashcards.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.springframework.stereotype.Component;

@Provider
@Component
public class DefaultExceptionHandler implements ExceptionMapper<GenericWebServiceException> {
	
    public Response toResponse(GenericWebServiceException exception) {

		// create a Response
		ResponseBuilder builder = Response.status(exception.getError().getErrorId());
		builder.entity(exception.getError());
		return builder.build();	
    }
}
