package org.gluecoders.library.testHelpers.rest;

import org.gluecoders.library.config.Oval;
import org.gluecoders.library.config.SpringSecurityAdapter;
import org.gluecoders.library.dao.CredentialsDao;
import org.gluecoders.library.rest.helper.Validator;
import org.gluecoders.library.security.PrincipalService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Anand_Rajneesh on 7/2/2017.
 */
@Configuration
@Import({Oval.class, Validator.class, SpringSecurityAdapter.class})
public class MvcBootstrap {

    @Bean
    public PrincipalService principalService(){
        return Mockito.mock(PrincipalService.class);
    }

    @Bean
    public CredentialsDao credentialsDao(){
        return Mockito.mock(CredentialsDao.class);
    }
}
