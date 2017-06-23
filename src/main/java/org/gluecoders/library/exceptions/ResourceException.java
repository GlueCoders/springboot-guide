package org.gluecoders.library.exceptions;

import java.util.function.IntSupplier;

/**
 * Created by Anand_Rajneesh on 6/16/2017.
 */
public abstract class ResourceException extends Exception{

    private final IntSupplier statusCodeSupplier;

    public ResourceException(String message, IntSupplier statusCodeSupplier) {
        super(message);
        this.statusCodeSupplier = statusCodeSupplier;
    }

    public ResourceException(String message, Throwable cause, IntSupplier statusCodeSupplier) {
        super(message, cause);
        this.statusCodeSupplier = statusCodeSupplier;
    }

    public ResourceException(Throwable cause, IntSupplier statusCodeSupplier) {
        super(cause);
        this.statusCodeSupplier = statusCodeSupplier;
    }

    public final int statusCode(){
        return statusCodeSupplier.getAsInt();
    }
}
