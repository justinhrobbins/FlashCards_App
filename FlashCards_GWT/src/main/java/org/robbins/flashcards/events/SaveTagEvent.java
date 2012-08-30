package org.robbins.flashcards.events;

import org.robbins.flashcards.model.Tag;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Here is a custom event. For comparison this is also a SaveTagEvent.
 * This event extends the Event from the web.bindery package.
 */
public class SaveTagEvent extends Event<SaveTagEventHandler> {

    private static final Type<SaveTagEventHandler> TYPE = new Type<SaveTagEventHandler>();

    /**
     * Register a handler for SaveTagEvent events on the eventbus.
     * 
     * @param eventBus the {@link EventBus}
     * @param handler an {@link SaveTagEvent.Handler} instance
     * @return an {@link HandlerRegistration} instance
     */
    public static HandlerRegistration register(EventBus eventBus, SaveTagEventHandler handler) {
      return eventBus.addHandler(TYPE, handler);
    }    

	public Tag getTag() {return tag;}
    private final Tag tag;
    
	public SaveTagEvent(Tag tag) {
        this.tag = tag;
    }

    @Override
    public Type<SaveTagEventHandler> getAssociatedType() {
        return TYPE;
    }


    @Override
    protected void dispatch(SaveTagEventHandler handler) {
        handler.onSaveTag(this);
    }
}