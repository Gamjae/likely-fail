package org.gamjae.makitfail.annotations;

import org.gamjae.makitfail.exceptions.SimulatedRuntimeException;
import org.gamjae.makitfail.exceptions.SimulatedTimeoutException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MakitFail {
    String[] profiles() default {};
    Class<? extends Throwable>[] exceptions() default {SimulatedRuntimeException.class, SimulatedTimeoutException.class};
    double failureRate() default 0.5;
}
