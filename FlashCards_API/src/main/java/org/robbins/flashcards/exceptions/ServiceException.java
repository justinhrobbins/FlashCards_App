
package org.robbins.flashcards.exceptions;

public class ServiceException extends FlashCardsException
{

    public ServiceException(final String message) {
        super(message);
    }

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
