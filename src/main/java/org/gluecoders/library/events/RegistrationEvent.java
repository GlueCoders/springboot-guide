package org.gluecoders.library.events;

import org.gluecoders.library.models.Member;
import org.gluecoders.library.services.notifiers.EventHandler;

/**
 * Created by Anand Rajneesh on 12/2/2017.
 */
public class RegistrationEvent extends Event {

    private final String username;

    public RegistrationEvent(Member member) {
        super(Events.NEW_MEMBER.get());
        username = member.getEmail();
    }

    @Override
    public void accept(EventHandler handler) {
        handler.handle(this);
    }

    public String getUsername() {
        return username;
    }
}
