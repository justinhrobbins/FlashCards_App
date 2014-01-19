
package org.robbins.flashcards.providers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.springframework.stereotype.Component;

@Provider
@Component
public class DefaultExceptionHandler implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException webApplicationException) {
        ResponseBuilder builder;
        GenericWebServiceException exception;

        if (webApplicationException instanceof GenericWebServiceException) {
            exception = (GenericWebServiceException) webApplicationException;
        } else {
            exception = new GenericWebServiceException(
                    Response.Status.fromStatusCode(webApplicationException.getResponse().getStatus()),
                    webApplicationException.getMessage());
        }

        builder = Response.status(exception.getError().getErrorId());
        builder.entity(exception.getError());
        return builder.build();
    }
}
