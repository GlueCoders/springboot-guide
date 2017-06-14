package org.gluecoders.library.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import static org.junit.Assert.*;
/**
 * Created by Anand_Rajneesh on 6/14/2017.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class SwaggerTest {

    @Test
    public void testSwaggerSpringMvcPlugin(){
        assertNotNull(new Swagger().swaggerSpringMvcPlugin());
    }
}
