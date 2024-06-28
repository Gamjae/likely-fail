package org.gamjae.makitfail.exceptions;

public class SimulatedRuntimeException extends RuntimeException {
    public SimulatedRuntimeException() {
        super("This is a simulated runtime exception");
    }
}
