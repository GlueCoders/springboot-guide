package org.gluecoders.library.rest.ext;

/**
 * Created by Anand_Rajneesh on 6/12/2017.
 */
@FunctionalInterface
public interface Function1<Arg1, R> extends Function{

    R apply(Arg1 arg);
}
