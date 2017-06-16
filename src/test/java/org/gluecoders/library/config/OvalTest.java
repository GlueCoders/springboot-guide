package org.gluecoders.library.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Anand_Rajneesh on 6/16/2017.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class OvalTest {

    @Test
    public void testValidator(){
        assertNotNull(new Oval().validator());
    }
}
