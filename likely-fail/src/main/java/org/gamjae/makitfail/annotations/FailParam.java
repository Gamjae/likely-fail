package org.gamjae.makitfail.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FailParam {

    /**
     * Profiles to match. If the active profiles match any of the profiles in {@code profiles}, the parameter will be checked.
     * If not, the parameter will be passed to the method. {@code profiles} can accept boolean operators like {@code &}.
     * <p> Example: {@code {"dev", "prod&!test"}} will match the profile if the active profile is {@code dev} or {@code prod} and not {@code test}.
     * <p> If {@code profiles} is empty, the parameter will be checked regardless of the active profiles.
     *
     */
    String[] profiles() default {};

    /**
     * Condition to fail. {@code condition} should be a SpEL boolean expression.
     * If the result of the expression is {@code true}, the parameter will throw an exception.
     * If not, the parameter will be passed to the method.
     *
     */
    String condition() default "false";

    /**
     * Exception to throw when the condition is met. If not specified, {@code RuntimeException} will be thrown.
     * Multiple exceptions can be specified. If multiple exceptions are specified, one of them will be thrown randomly.
     */
    Class<? extends Throwable>[] exceptions() default {RuntimeException.class};
}
