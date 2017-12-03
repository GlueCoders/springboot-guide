package org.gluecoders.library.services.notifiers;

import org.gluecoders.library.events.RegistrationEvent;
import org.gluecoders.library.models.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Anand Rajneesh on 12/3/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ActiveMQAutoConfiguration.class, JmsAutoConfiguration.class, JmsSenderTest.JmsConfiguration.class})
public class JmsSenderTest {

    @Configuration
    @ComponentScan(basePackages = {"org.gluecoders.library.services.notifiers"})
    static class JmsConfiguration{}

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsSender jmsSender;

    @Test
    public void testHandle() throws Exception {
        Member m = new Member();
        m.setEmail("email");
        RegistrationEvent event = new RegistrationEvent(m);
        jmsSender.handle(event);
    }
}