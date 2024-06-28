package org.gamjae.makitfail.utils;

public class ExceptionThrowUtil {
    public static void throwRandomException(Class<? extends Throwable>[] exceptions) throws Throwable {
        int randomIndex = (int) (Math.random() * exceptions.length);
        throw exceptions[randomIndex].getConstructor().newInstance();
    }
}
