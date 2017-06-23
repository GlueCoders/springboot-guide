package org.gluecoders.library.exceptions;

import net.sf.oval.ConstraintViolation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Anand_Rajneesh on 6/16/2017.
 */
public class ValidationException extends ResourceException {

    public ValidationException(String message, StatusCode statusCode) {
        super(message, statusCode);
    }

    public static ValidationException of(List<ConstraintViolation> violations){
       return of(violations, StatusCode.BAD_REQUEST);
    }

    public static ValidationException of(List<ConstraintViolation> violations, StatusCode statusCode){
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        return new ValidationException(message, statusCode);
    }

}
