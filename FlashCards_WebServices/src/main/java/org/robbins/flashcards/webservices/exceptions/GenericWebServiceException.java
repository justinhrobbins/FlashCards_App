package org.robbins.flashcards.webservices.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class GenericWebServiceException extends WebApplicationException {

	private static final long serialVersionUID = -922372322231840684L;

	private WebServicesError error;
	
	public GenericWebServiceException(Response.Status status, WebServicesError error) {
		this.setError(error);
	}

//	public GenericWebServiceException(Response.Status status, List<WebServicesError> errors) {
//		throwError(status, errors);
//	}

	public GenericWebServiceException(Response.Status status, String customMessage) {
		this.setError(new WebServicesError(status.getStatusCode(), status.getReasonPhrase(), customMessage));
	}

	public GenericWebServiceException(Response.Status status, Exception e) {
		this.setError(new WebServicesError(status.getStatusCode(), status.getReasonPhrase(), e.getMessage()));
	}

	public GenericWebServiceException(Response.Status status, String customMessage, Exception e) {
		this.setError(new WebServicesError(status.getStatusCode(), status.getReasonPhrase(), customMessage));
	}

	public WebServicesError getError() {
		return error;
	}

	public void setError(WebServicesError error) {
		this.error = error;
	}
}
