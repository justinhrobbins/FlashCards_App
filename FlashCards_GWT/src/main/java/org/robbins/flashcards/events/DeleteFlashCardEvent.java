
package org.robbins.flashcards.events;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Here is a custom event. For comparison this is also a DeleteFlashCardEvent. This event
 * extends the Event from the web.bindery package.
 */
public class DeleteFlashCardEvent extends Event<DeleteFlashCardEventHandler> {

    private static final Type<DeleteFlashCardEventHandler> TYPE = new Type<DeleteFlashCardEventHandler>();

    /**
     * Register a handler for DeleteFlashCardEvent events on the eventbus.
     *
     * @param eventBus the {@link EventBus}
     * @param handler an {@link DeleteFlashCardEvent.Handler} instance
     * @return an {@link HandlerRegistration} instance
     */
    public static HandlerRegistration register(final EventBus eventBus,
            final DeleteFlashCardEventHandler handler) {
        return eventBus.addHandler(TYPE, handler);
    }

    public Long getFlashCardId() {
        return FlashCardId;
    }

    private final Long FlashCardId;

    public DeleteFlashCardEvent(final Long FlashCardId) {
        this.FlashCardId = FlashCardId;
    }

    @Override
    public Type<DeleteFlashCardEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final DeleteFlashCardEventHandler handler) {
        handler.onDeleteFlashcard(this);
    }
}
