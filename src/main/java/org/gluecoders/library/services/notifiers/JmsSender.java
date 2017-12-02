package org.gluecoders.library.services.notifiers;

import org.gluecoders.library.events.RegistrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Anand Rajneesh on 12/2/2017.
 */
@Component
public class JmsSender implements EventHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void handle(RegistrationEvent event) {
        jmsTemplate.convertAndSend("members", event);
        LOGGER.info("Registration event send to destination : members");
    }
}
