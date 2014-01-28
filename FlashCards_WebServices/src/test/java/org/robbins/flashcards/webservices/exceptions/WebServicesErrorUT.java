
package org.robbins.flashcards.webservices.exceptions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.tests.UnitTest;

@Category(UnitTest.class)
public class WebServicesErrorUT {

    @Test
    public void constuctor() {
        WebServicesError error = new WebServicesError(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                Response.Status.INTERNAL_SERVER_ERROR.getFamily().toString());

        assertThat(error.getErrorId(),
                is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        assertThat(error.getErrorName(),
                is(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        assertThat(error.getErrorMessage(),
                is(Response.Status.INTERNAL_SERVER_ERROR.getFamily().toString()));
    }

    @Test
    public void errorId() {
        WebServicesError error = new WebServicesError();
        error.setErrorId(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        assertThat(error.getErrorId(),
                is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
    }

    @Test
    public void errorName() {
        WebServicesError error = new WebServicesError();
        error.setErrorName(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());

        assertThat(error.getErrorName(),
                is(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Test
    public void errorMessage() {
        WebServicesError error = new WebServicesError();
        error.setErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getFamily().toString());

        assertThat(error.getErrorMessage(),
                is(Response.Status.INTERNAL_SERVER_ERROR.getFamily().toString()));
    }
}
