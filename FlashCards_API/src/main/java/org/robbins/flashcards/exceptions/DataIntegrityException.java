
package org.robbins.flashcards.exceptions;

public class DataIntegrityException extends RuntimeException {

    private static final long serialVersionUID = 1728400692558481444L;

    public DataIntegrityException(final String message) {
        super(message);
    }

    public DataIntegrityException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
