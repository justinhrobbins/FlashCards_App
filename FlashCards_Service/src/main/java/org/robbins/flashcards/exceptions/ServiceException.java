
package org.robbins.flashcards.exceptions;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1728400692558481444L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
