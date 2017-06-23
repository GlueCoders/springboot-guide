package org.gluecoders.library.rest.helper;

import org.gluecoders.library.exceptions.ResourceException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by Anand_Rajneesh on 6/23/2017.
 */
@RestControllerAdvice
public class ResourceExceptionMapper {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity handle(ResourceException e){
        return ResponseEntity
                .status(e.statusCode())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(String.format("{\"error\":\"%s\"}", e.getMessage()));
    }
}
