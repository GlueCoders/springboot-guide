package org.gluecoders.library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gluecoders.library.exceptions.ResourceAlreadyExistsException;
import org.gluecoders.library.models.Credentials;
import org.gluecoders.library.models.Member;
import org.gluecoders.library.services.RegistrationService;
import org.gluecoders.library.testHelpers.rest.MvcBootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Anand_Rajneesh on 7/2/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(Registration.class)
@Import({MvcBootstrap.class})
public class RegistrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "registrationService")
    private RegistrationService registrationService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testRegistration_Positive() throws Exception {
        when(registrationService.register(any())).thenAnswer(invocationOnMock -> {
            Credentials credential = (Credentials) invocationOnMock.getArguments()[0];
            Member m = new Member();
            m.setEmail(credential.getUsername());
            return m;
        });
        Credentials c = new Credentials();
        c.setUsername("username");
        c.setPwd("password");
        Member m = new Member();
        m.setEmail(c.getUsername());
        mvc
                .perform(post("/unsecured/register")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(mapper.writeValueAsString(c)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapper.writeValueAsString(m)));
        verify(registrationService, times(1)).register(any());
    }

    @Test
    public void testRegistration_InvalidParamsUsernameMissing() throws Exception {
        when(registrationService.register(any())).thenAnswer(invocationOnMock -> {
            Credentials credential = (Credentials) invocationOnMock.getArguments()[0];
            Member m = new Member();
            m.setEmail(credential.getUsername());
            return m;
        });
        Credentials c = new Credentials();
        c.setPwd("password");
        mvc
                .perform(post("/unsecured/register")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(c)))
                .andExpect(status().isBadRequest());
        verify(registrationService, never()).register(c);
    }

    @Test
    public void testRegistration_InvalidParamsPasswordMissing() throws Exception {
        when(registrationService.register(any())).thenAnswer(invocationOnMock -> {
            Credentials credential = (Credentials) invocationOnMock.getArguments()[0];
            Member m = new Member();
            m.setEmail(credential.getUsername());
            return m;
        });
        Credentials c = new Credentials();
        c.setUsername("username");
        mvc
                .perform(post("/unsecured/register")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(c)))
                .andExpect(status().isBadRequest());
        verify(registrationService, never()).register(c);
    }

    @Test
    public void testRegistration_UserAlreadyRegistered() throws Exception {
        when(registrationService.register(any())).thenThrow(new ResourceAlreadyExistsException("User already registered"));
        Credentials c = new Credentials();
        c.setUsername("username");
        c.setPwd("password");
        mvc
                .perform(post("/unsecured/register")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(c)))
                .andExpect(status().isConflict());
        verify(registrationService, times(1)).register(any());
    }
}
