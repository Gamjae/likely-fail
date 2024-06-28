package org.gamjae.makitfail.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MakitFail {

    /**
     * Profiles to match. If the active profiles match any of the profiles in {@code profiles}, it will check failure probability.
     * If not, the parameter will be passed to the method. {@code profiles} can accept boolean operators like {@code &}.
     * <p> Example: {@code {"dev", "prod&!test"}} will match the profile if the active profile is {@code dev} or {@code prod} and not {@code test}.
     * <p> If {@code profiles} is empty, failure probability will be checked regardless of the active profiles.
     *
     */
    String[] profiles() default {};

    /**
     * Failure rate. If the random number is less than {@code failureRate}, the method will throw an exception.
     */
    double failureRate() default 0.5;

    /**
     * Exception to throw when the condition is met. If not specified, {@code RuntimeException} will be thrown.
     * Multiple exceptions can be specified. If multiple exceptions are specified, one of them will be thrown randomly.
     */
    Class<? extends Throwable>[] exceptions() default {RuntimeException.class};
}
