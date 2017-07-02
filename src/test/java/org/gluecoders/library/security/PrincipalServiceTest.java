package org.gluecoders.library.security;

import org.gluecoders.library.dao.CredentialsDao;
import org.gluecoders.library.models.Credentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Anand_Rajneesh on 7/2/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PrincipalServiceTest {

    @Mock
    private CredentialsDao credentialsDao;

    @InjectMocks
    private PrincipalService principalService;


    @Test
    public void testFindUser_Positive(){
        when(credentialsDao.findDistinctByUsername(any())).thenReturn(new Credentials());
        Credentials c = principalService.findUser("username");
        assertNotNull("Credentials should not be null", c);
        verify(credentialsDao, times(1)).findDistinctByUsername(anyString());
    }

    @Test
    public void testFindUser_NoUserAvailable(){
        when(credentialsDao.findDistinctByUsername(any())).thenReturn(null);
        Credentials c = principalService.findUser("username");
        assertNull("Credentials should be null", c);
        verify(credentialsDao, times(1)).findDistinctByUsername(anyString());
    }
}
