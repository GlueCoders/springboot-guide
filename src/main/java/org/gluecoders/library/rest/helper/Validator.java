package org.gluecoders.library.rest.helper;

import net.sf.oval.ConstraintViolation;
import org.gluecoders.library.exceptions.ResourceException;
import org.gluecoders.library.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Anand_Rajneesh on 6/16/2017.
 */
@Component
public final class Validator {

    private final net.sf.oval.Validator validator;

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    @Autowired
    public Validator(net.sf.oval.Validator validator) {
        this.validator = validator;
    }

    private <T> List<ConstraintViolation> check(T t){
        return validator.validate(t);
    }

    public <T, E extends ResourceException> void validate(T t, Function<List<ConstraintViolation>, E> exceptionSupplier) throws E {
        List<ConstraintViolation> violations = check(t);
        if(!violations.isEmpty()){
            LOGGER.error("{} has {} validation errors", t, violations.size());
            throw exceptionSupplier.apply(violations);
        }
    }

    public <T> void validate(T t) throws ValidationException{
        validate(t, ValidationException::of);
    }

}
