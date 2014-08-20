
package org.robbins.flashcards.webservices.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class GenericWebServiceException extends WebApplicationException {

    private static final long serialVersionUID = -922372322231840684L;

    private WebServicesError error;

    public GenericWebServiceException(final Response.Status status,
            final WebServicesError error) {
        this.setError(error);
    }

    // public GenericWebServiceException(Response.Status status, List<WebServicesError>
    // errors) {
    // throwError(status, errors);
    // }

    public GenericWebServiceException(final Response.Status status,
            final String customMessage) {
        this.setError(new WebServicesError(status.getStatusCode(),
                status.getReasonPhrase(), customMessage));
    }

    public GenericWebServiceException(final Response.Status status, final Exception e) {
        this.setError(new WebServicesError(status.getStatusCode(),
                status.getReasonPhrase(), e.getMessage()));
    }

    public GenericWebServiceException(final Response.Status status,
            final String customMessage, final Exception e) {
        this.setError(new WebServicesError(status.getStatusCode(),
                status.getReasonPhrase(), customMessage));
    }

    public WebServicesError getError() {
        return error;
    }

    public void setError(final WebServicesError error) {
        this.error = error;
    }
}
