package org.gamjae.likelyfail;

import org.gamjae.likelyfail.exceptions.SimulatedRuntimeException;
import org.gamjae.likelyfail.exceptions.SimulatedTimeoutException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LikelyFail {
    String[] profiles() default {};
    Class<? extends Throwable>[] exceptions() default {SimulatedRuntimeException.class, SimulatedTimeoutException.class};
    double failureRate() default 0.5;
}
