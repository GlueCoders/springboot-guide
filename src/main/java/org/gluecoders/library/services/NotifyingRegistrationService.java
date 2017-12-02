package org.gluecoders.library.services;

import org.gluecoders.library.events.RegistrationEvent;
import org.gluecoders.library.exceptions.ResourceAlreadyExistsException;
import org.gluecoders.library.models.Credentials;
import org.gluecoders.library.models.Member;
import org.gluecoders.library.services.notifiers.JmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Anand Rajneesh on 12/2/2017.
 */
@Component("registrationService")
public class NotifyingRegistrationService implements RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyingRegistrationService.class);

    @Autowired
    @Qualifier("defaultRegistrationService")
    private RegistrationService registrationService;

    @Autowired
    private JmsSender jmsSender;

    @Override
    public Member register(Credentials credentials) throws ResourceAlreadyExistsException {
        Member member = registrationService.register(credentials);
        LOGGER.info("Notifying registration event");
        RegistrationEvent registrationEvent = new RegistrationEvent(member);
        registrationEvent.accept(jmsSender);
        return member;
    }
}
