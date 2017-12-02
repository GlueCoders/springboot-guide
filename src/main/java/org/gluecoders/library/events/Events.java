package org.gluecoders.library.events;

import java.util.function.Supplier;

/**
 * Created by Anand Rajneesh on 12/2/2017.
 */
public enum Events implements Supplier<String> {

    NEW_MEMBER;

    @Override
    public String get() {
        return this.name();
    }
}
