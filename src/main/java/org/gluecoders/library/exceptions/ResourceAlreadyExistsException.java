package org.gluecoders.library.exceptions;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
public class ResourceAlreadyExistsException extends ResourceException {

    public ResourceAlreadyExistsException(String message) {
        super(message, StatusCode.CONFLICT);
    }

}
