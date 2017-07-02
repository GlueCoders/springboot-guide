package org.gluecoders.library.dao;

import org.gluecoders.library.models.Credentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
@Repository
public interface CredentialsDao extends MongoRepository<Credentials, String> {

    Credentials findDistinctByUsername(String username);
}
