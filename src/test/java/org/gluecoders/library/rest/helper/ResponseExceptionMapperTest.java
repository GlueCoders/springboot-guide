package org.gluecoders.library.rest.helper;

import org.gluecoders.library.exceptions.ResourceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.*;
/**
 * Created by Anand_Rajneesh on 6/23/2017.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ResponseExceptionMapperTest {

    private ResourceExceptionMapper testObject;

    @Before
    public void init(){
        testObject = new ResourceExceptionMapper();
    }

    @Test
    public void test(){
        ResponseEntity response = testObject.handle(new ResourceException("Error",()->400) {
            public String getMessage() {
                return super.getMessage();
            }
        });
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertTrue(response.hasBody());
    }
}
