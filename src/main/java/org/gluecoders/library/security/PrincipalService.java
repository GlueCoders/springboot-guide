package org.gluecoders.library.security;

import org.gluecoders.library.dao.CredentialsDao;
import org.gluecoders.library.models.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
@Component
public class PrincipalService  {

    @Autowired
    private CredentialsDao credentialsDao;

    public Credentials findUser(String username){
        return credentialsDao.findDistinctByUsername(username);
    }


}
