
package org.robbins.flashcards.exceptions;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1728400692558481444L;

    public ServiceException(final String message) {
        super(message);
    }

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
