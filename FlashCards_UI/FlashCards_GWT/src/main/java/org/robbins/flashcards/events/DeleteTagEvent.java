
package org.robbins.flashcards.events;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Here is a custom event. For comparison this is also a DeleteTagEvent. This event
 * extends the Event from the web.bindery package.
 */
public class DeleteTagEvent extends Event<DeleteTagEventHandler> {

    private static final Type<DeleteTagEventHandler> TYPE = new Type<DeleteTagEventHandler>();

    /**
     * Register a handler for DeleteTagEvent events on the eventbus.
     *
     * @param eventBus the {@link EventBus}
     * @param handler an {@link DeleteTagEvent.Handler} instance
     * @return an {@link HandlerRegistration} instance
     */
    public static HandlerRegistration register(final EventBus eventBus,
            final DeleteTagEventHandler handler) {
        return eventBus.addHandler(TYPE, handler);
    }

    public String getTagId() {
        return tagId;
    }

    private final String tagId;

    public DeleteTagEvent(final String tagId) {
        this.tagId = tagId;
    }

    @Override
    public Type<DeleteTagEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final DeleteTagEventHandler handler) {
        handler.onDeleteTag(this);
    }
}
