
package org.robbins.flashcards.webservices.cxf.providers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.providers.DefaultExceptionHandler;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.flashcards.webservices.exceptions.WebServicesError;
import org.robbins.tests.UnitTest;

@Category(UnitTest.class)
public class DefaultExceptionHandlerUT {

    private final DefaultExceptionHandler handler = new DefaultExceptionHandler();

    private GenericWebServiceException exception;

    @Test
    public void toResponse_withUnexpectedException() {

        WebApplicationException webApplicationException = new WebApplicationException();

        Response result = handler.toResponse(webApplicationException);

        WebServicesError webServicesError = (WebServicesError) result.getEntity();

        assertThat(webServicesError, is(WebServicesError.class));
        assertThat(webServicesError.getErrorId(),
                is(Status.INTERNAL_SERVER_ERROR.getStatusCode()));
    }

    @Test
    public void toResponse_withUnexpectedExceptionAndMessage() {

        Throwable throwable = new Throwable("error_message");
        WebApplicationException webApplicationException = new WebApplicationException(
                throwable);

        Response result = handler.toResponse(webApplicationException);

        WebServicesError webServicesError = (WebServicesError) result.getEntity();

        assertThat(webServicesError, is(WebServicesError.class));
        assertThat(webServicesError.getErrorId(),
                is(Status.INTERNAL_SERVER_ERROR.getStatusCode()));
    }

    @Test
    public void toResponse_withExpectedException() {
        exception = new GenericWebServiceException(Status.NOT_FOUND,
                Status.NOT_FOUND.getReasonPhrase());

        Response result = handler.toResponse(exception);

        WebServicesError webServicesError = (WebServicesError) result.getEntity();

        assertThat(webServicesError, is(WebServicesError.class));
        assertThat(webServicesError.getErrorId(), is(Status.NOT_FOUND.getStatusCode()));
    }
}
