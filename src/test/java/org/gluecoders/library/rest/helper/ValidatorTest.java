package org.gluecoders.library.rest.helper;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.constraint.AssertCheck;
import org.gluecoders.library.exceptions.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Created by Anand_Rajneesh on 6/23/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ValidatorTest {

    @Mock
    private net.sf.oval.Validator validator;

    @InjectMocks
    private Validator testObject;

    private static class Behavior {
        private net.sf.oval.Validator validator;

        public static Behavior set(net.sf.oval.Validator validator) {
            Behavior b = new Behavior();
            b.validator = validator;
            return b;
        }

        public Behavior failOnValidation() {
            when(validator.validate(any())).thenReturn(Collections.singletonList(new ConstraintViolation(new AssertCheck(), null, null, null, null)));
            return this;
        }

        public Behavior passOnValidation() {
            when(validator.validate(any())).thenReturn(Collections.emptyList());
            return this;
        }
    }

    @Test
    public void testOverloadedValidate_ValidObject() throws ValidationException {
        Behavior.set(validator).passOnValidation();
        Object o = new Object();
        testObject.validate(o, ValidationException::of);
        verify(validator, times(1)).validate(o);
    }

    @Test(expected = ValidationException.class)
    public void testOverloadedValidate_InvalidObject() throws ValidationException {
        Behavior.set(validator).failOnValidation();
        Object o = new Object();
        try {
            testObject.validate(o, ValidationException::of);
        } catch (ValidationException e) {
            verify(validator, times(1)).validate(o);
            throw e;
        }
    }


    @Test
    public void testValidate_ValidObject() throws ValidationException {
        Behavior.set(validator).passOnValidation();
        Object o = new Object();
        testObject.validate(o);
        verify(validator, times(1)).validate(o);
    }

    @Test(expected = ValidationException.class)
    public void testValidate_InvalidObject() throws ValidationException {
        Behavior.set(validator).failOnValidation();
        Object o = new Object();
        try {
            testObject.validate(o);
        } catch (ValidationException e) {
            verify(validator, times(1)).validate(o);
            throw e;
        }
    }
}
