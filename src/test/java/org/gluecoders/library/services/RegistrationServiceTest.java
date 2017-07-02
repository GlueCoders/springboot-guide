package org.gluecoders.library.services;

import org.gluecoders.library.dao.CredentialsDao;
import org.gluecoders.library.dao.MemberDao;
import org.gluecoders.library.exceptions.ResourceAlreadyExistsException;
import org.gluecoders.library.models.Credentials;
import org.gluecoders.library.models.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Anand_Rajneesh on 7/2/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    @Mock
    private CredentialsDao credentialsDao;

    @Mock
    private MemberDao memberDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DefaultRegistrationService testObject;

    private Credentials c;

    @Before
    public void init(){
        c = new Credentials();
        c.setUsername("username");
        c.setPwd("password");
    }

    @Test
    public void testRegister_Positive() throws ResourceAlreadyExistsException {
        when(credentialsDao.findDistinctByUsername(c.getUsername())).thenReturn(null);
        when(credentialsDao.save(c)).thenReturn(c);
        when(memberDao.save(any(Member.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        when(passwordEncoder.encode(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        Member m = testObject.register(c);
        assertNotNull("Salted pwd should be set", c.getSaltedPwd());
        assertNotNull("Role should be set", c.getRole());
        assertNotNull("Member should not be null", m);
        assertEquals("Member username should be same as credentials username ",m.getEmail(), c.getUsername());
        verify(credentialsDao , times(1)).findDistinctByUsername(c.getUsername());
        verify(credentialsDao , times(1)).save(c);
        verify(memberDao, times(1)).save(m);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testRegister_UserAlreadyExists() throws ResourceAlreadyExistsException {
        when(credentialsDao.findDistinctByUsername(c.getUsername())).thenReturn(c);
        try {
            testObject.register(c);
        }catch (ResourceAlreadyExistsException e) {
            assertNull("Salted pwd should not be set", c.getSaltedPwd());
            assertNull("Role should not be set", c.getRole());
            verify(credentialsDao, times(1)).findDistinctByUsername(c.getUsername());
            verify(credentialsDao, never()).save(any(Credentials.class));
            verify(memberDao, never()).save(any(Member.class));
            throw e;
        }
    }
}
