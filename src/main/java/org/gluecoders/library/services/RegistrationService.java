package org.gluecoders.library.services;

import org.gluecoders.library.exceptions.ResourceAlreadyExistsException;
import org.gluecoders.library.models.Credentials;
import org.gluecoders.library.models.Member;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
public interface RegistrationService {

    Member register(Credentials credentials) throws ResourceAlreadyExistsException;
}
