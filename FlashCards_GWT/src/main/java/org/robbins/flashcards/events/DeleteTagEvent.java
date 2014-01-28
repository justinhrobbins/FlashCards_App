
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
    public static HandlerRegistration register(EventBus eventBus,
            DeleteTagEventHandler handler) {
        return eventBus.addHandler(TYPE, handler);
    }

    public Long getTagId() {
        return tagId;
    }

    private final Long tagId;

    public DeleteTagEvent(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public Type<DeleteTagEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeleteTagEventHandler handler) {
        handler.onDeleteTag(this);
    }
}
