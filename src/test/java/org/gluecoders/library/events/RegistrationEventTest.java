package org.gluecoders.library.events;

import org.gluecoders.library.models.Member;
import org.gluecoders.library.services.notifiers.EventHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by Anand Rajneesh on 12/3/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistrationEventTest {

    private static class MockEventHandler implements EventHandler{

        @Override
        public void handle(RegistrationEvent event) {

        }
    }

    private MockEventHandler eventHandler;

    @Before
    public void setup(){
        eventHandler = Mockito.spy(new MockEventHandler());
    }

    @Test
    public void testRegistrationEvent_SetsUsername(){
        Member m = new Member();
        m.setEmail("email");
        RegistrationEvent registrationEvent = new RegistrationEvent(m);
        assertNotNull("Eventtype should never be null", registrationEvent.getEventType());
        assertNotNull("Username should not be null if present in member", registrationEvent.getUsername());
    }

    @Test
    public void testAccept(){
        Member m = new Member();
        m.setEmail("email");
        RegistrationEvent registrationEvent = new RegistrationEvent(m);
        registrationEvent.accept(eventHandler);
        verify(eventHandler, times(1)).handle(registrationEvent);
    }

}
