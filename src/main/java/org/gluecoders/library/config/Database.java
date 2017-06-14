package org.gluecoders.library.config;

import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.time.YearMonth;
import java.util.Arrays;

/**
 * Created by Anand_Rajneesh on 6/14/2017.
 */
@Configuration
public class Database{

    public enum YearMonthConverter implements Converter<BasicDBObject, YearMonth> {
        INSTANCE;

        private YearMonthConverter(){}

        @Override
        public YearMonth convert(BasicDBObject source) {
            return YearMonth.of(
                    (int) source.get("year"),
                    (int) source.get("month")
            );
        }
    }

    @Bean
    public CustomConversions customConversions(){
        return new CustomConversions(Arrays.asList(YearMonthConverter.INSTANCE));
    }

}
