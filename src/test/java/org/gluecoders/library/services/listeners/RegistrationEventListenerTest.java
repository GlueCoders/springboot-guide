package org.gluecoders.library.services.listeners;

import org.gluecoders.library.events.RegistrationEvent;
import org.gluecoders.library.models.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

/**
 * Created by Anand Rajneesh on 12/3/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ActiveMQAutoConfiguration.class, JmsAutoConfiguration.class, RegistrationEventListenerTest.JmsConfiguration.class})
public class RegistrationEventListenerTest {

    @Configuration
    @ComponentScan(basePackages = {"org.gluecoders.library.services.listeners"})
    static class JmsConfiguration {
    }

    @Autowired
    private RegistrationEventListener listener;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JmsTemplate template;

    @Test
    public void testReceive() throws Exception {
        listener = Mockito.spy(listener);
        RegistrationEvent event = new RegistrationEvent(new Member());
        template.convertAndSend("members", event);
        //Mockito.verify(listener, times(1)).receive(any());
    }

}