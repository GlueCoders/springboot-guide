package org.gluecoders.library.config;

import net.sf.oval.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Anand_Rajneesh on 6/15/2017.
 */
@Configuration
public class Oval {

    @Bean
    public Validator validator(){
        return new Validator();
    }

}
