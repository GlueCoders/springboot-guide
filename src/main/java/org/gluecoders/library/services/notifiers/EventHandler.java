package org.gluecoders.library.services.notifiers;

import org.gluecoders.library.events.RegistrationEvent;

/**
 * Created by Anand Rajneesh on 12/2/2017.
 */
public interface EventHandler {

    void handle(RegistrationEvent event);
}
