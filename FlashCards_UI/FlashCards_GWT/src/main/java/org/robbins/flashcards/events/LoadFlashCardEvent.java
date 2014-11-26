
package org.robbins.flashcards.events;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Here is a custom event. For comparison this is also a LoadTagEvent. This event extends
 * the Event from the web.bindery package.
 */
public class LoadFlashCardEvent extends Event<LoadFlashCardEventHandler> {

    private static final Type<LoadFlashCardEventHandler> TYPE = new Type<LoadFlashCardEventHandler>();

    /**
     * Register a handler for LoadTagEvent events on the eventbus.
     *
     * @param eventBus the {@link EventBus}
     * @param handler an {@link LoadTagEvent.Handler} instance
     * @return an {@link HandlerRegistration} instance
     */
    public static HandlerRegistration register(final EventBus eventBus,
            final LoadFlashCardEventHandler handler) {
        return eventBus.addHandler(TYPE, handler);
    }

    public Long getFlashCardId() {
        return flashCardId;
    }

    private final Long flashCardId;

    public LoadFlashCardEvent(final Long flashCardId) {
        this.flashCardId = flashCardId;
    }

    @Override
    public Type<LoadFlashCardEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final LoadFlashCardEventHandler handler) {
        handler.onLoadFlashCard(this);
    }
}
