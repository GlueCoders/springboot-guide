package org.gluecoders.library.events;

import org.gluecoders.library.services.notifiers.EventHandler;

import java.io.Serializable;

/**
 * Created by Anand Rajneesh on 12/2/2017.
 */
public abstract class Event implements Serializable{

    protected final String eventType;

    public Event(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public abstract void accept(EventHandler handler);
}
