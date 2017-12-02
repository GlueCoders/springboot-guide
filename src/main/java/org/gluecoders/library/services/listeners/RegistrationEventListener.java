package org.gluecoders.library.services.listeners;

import org.gluecoders.library.events.RegistrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by Anand Rajneesh on 12/2/2017.
 */
@Component
public class RegistrationEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationEventListener.class);

    @JmsListener(destination = "members")
    public void receive(RegistrationEvent event){
        LOGGER.info("Received event {} from members queue", event.getEventType());
    }
}
