package org.gluecoders.library.exceptions;

import java.util.function.IntSupplier;

/**
 * Created by Anand_Rajneesh on 6/23/2017.
 */
public enum StatusCode implements IntSupplier{
    BAD_REQUEST(400),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500);

    @Override
    public int getAsInt() {
        return this.statusCode;
    }

    private int statusCode;

    StatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
