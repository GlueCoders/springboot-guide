package org.gluecoders.library.rest.ext;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Anand_Rajneesh on 6/12/2017.
 */
public class Runner<Arg, Result> {

    private Arg arg;
    private Function1<Arg, Result> handler;
    private Result result;

    public static <Arg,R> Runner args(Arg arg){
        Runner runner = new Runner();
        runner.arg = arg;
        return runner;
    }

    public Runner onException(Class<? extends Exception> exceptionClass){
        return this;
    }

    public Runner handle(Function1<Arg, Result> fn){
        this.handler = fn;
        return this;
    }

    public ResponseEntity<Result> exec(){
        try{
            result = handler.apply(arg);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }








}
