package org.gluecoders.library.rest.ext;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anand_Rajneesh on 6/12/2017.
 */
public class ResponseEntityWrapper {

    public void so(){
        Exception e = new Exception();
        Map<Class<? extends Exception>, Function1<Exception, ResponseEntity>> mapOfExceptions = new HashMap<>();
        mapOfExceptions.get(e.getClass());
    }
}
