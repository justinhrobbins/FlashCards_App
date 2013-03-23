package org.robbins.flashcards.webservices.exceptions;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.robbins.flashcards.jackson.CustomObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericWebServiceException extends WebApplicationException {

	private static Logger logger = Logger.getLogger(GenericWebServiceException.class);
	
	private static final long serialVersionUID = -922372322231840684L;

	public GenericWebServiceException(Response.Status status, WebServicesError error) {
		throwError(status, error);
	}

	public GenericWebServiceException(Response.Status status, List<WebServicesError> errors) {
		throwError(status, errors);
	}
	
	public GenericWebServiceException(Response.Status status, String message) {
		// create an Error object
		WebServicesError error = new WebServicesError(status.getStatusCode(), status.getReasonPhrase(), message);
		
		throwError(status, error);
	}
	
	private void throwError(Response.Status status, Object object) {
		// convert to Json
		ObjectMapper mapper = new CustomObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException();
		}
		
		// create a Response
		ResponseBuilder builder = Response.status(status);
		builder.entity(json);
		builder.type(MediaType.APPLICATION_JSON);
		Response response = builder.build();
		
		throw new WebApplicationException(response);
	}
}
