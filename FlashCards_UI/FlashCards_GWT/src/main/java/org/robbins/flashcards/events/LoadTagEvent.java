
package org.robbins.flashcards.events;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Here is a custom event. For comparison this is also a LoadTagEvent. This event extends
 * the Event from the web.bindery package.
 */
public class LoadTagEvent extends Event<LoadTagEventHandler> {

    private static final Type<LoadTagEventHandler> TYPE = new Type<LoadTagEventHandler>();

    /**
     * Register a handler for LoadTagEvent events on the eventbus.
     *
     * @param eventBus the {@link EventBus}
     * @param handler an {@link LoadTagEvent.Handler} instance
     * @return an {@link HandlerRegistration} instance
     */
    public static HandlerRegistration register(final EventBus eventBus,
            final LoadTagEventHandler handler) {
        return eventBus.addHandler(TYPE, handler);
    }

    public Long getTagId() {
        return tagId;
    }

    private final Long tagId;

    public LoadTagEvent(final Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public Type<LoadTagEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final LoadTagEventHandler handler) {
        handler.onLoadTag(this);
    }
}
