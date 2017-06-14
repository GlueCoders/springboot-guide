package org.gluecoders.library.dao.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Created by Anand_Rajneesh on 6/14/2017.
 */
@Configuration
public class FakeMongo extends AbstractMongoConfiguration{

    @Override
    protected String getDatabaseName() {
        return "mockDB";
    }

    @Bean
    public MongoClient mongo() {
        Fongo fongo = new Fongo("mockDB");
        return fongo.getMongo();
    }

}
