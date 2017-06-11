package org.gluecoders.library;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * Created by Anand_Rajneesh on 6/10/2017.
 */
@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Test
    public void testStartup() throws Exception {
        Application.main(new String[]{});
    }
}
