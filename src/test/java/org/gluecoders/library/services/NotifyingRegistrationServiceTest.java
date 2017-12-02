package org.gluecoders.library.services;

import org.gluecoders.library.models.Credentials;
import org.gluecoders.library.models.Member;
import org.gluecoders.library.services.notifiers.JmsSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by Anand Rajneesh on 12/3/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class NotifyingRegistrationServiceTest {

    @Mock
    private DefaultRegistrationService defaultRegistrationService;

    @Mock
    private JmsSender jmsSender;

    @InjectMocks
    private NotifyingRegistrationService testObject;

    @Before
    public void setup(){
        doNothing().when(jmsSender).handle(any());
    }

    @Test
    public void testRegister_DelegateSuccess_JmsSuccess() throws Exception {
        Credentials c = new Credentials();
        c.setPwd("pwd");
        c.setUsername("username");
        Member m = new Member();
        m.setEmail(c.getUsername());
        doReturn(m).when(defaultRegistrationService).register(c);
        m = testObject.register(c);
        assertNotNull(m);
        verify(defaultRegistrationService, times(1)).register(c);
        verify(jmsSender, times(1)).handle(any());
    }

    @Test(expected = Exception.class)
    public void testRegister_DelegateFails() throws Exception {
        Credentials c = new Credentials();
        c.setPwd("pwd");
        c.setUsername("username");
        Member m = new Member();
        m.setEmail(c.getUsername());
        doThrow(Exception.class).when(defaultRegistrationService).register(c);
        try {
            testObject.register(c);
        }catch (Exception e){
            verify(defaultRegistrationService, times(1)).register(c);
            verify(jmsSender, never()).handle(any());
            throw e;
        }
    }

    @Test(expected = Exception.class)
    public void testRegister_DelegateSuccess_JmsFails() throws Exception {
        Credentials c = new Credentials();
        c.setPwd("pwd");
        c.setUsername("username");
        Member m = new Member();
        m.setEmail(c.getUsername());
        doReturn(m).when(defaultRegistrationService).register(c);
        doThrow(Exception.class).when(jmsSender).handle(any());
        try {
            testObject.register(c);
        }catch (Exception e){
            verify(defaultRegistrationService, times(1)).register(c);
            verify(jmsSender, times(1)).handle(any());
            throw e;
        }
    }
}