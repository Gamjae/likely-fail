package org.gamjae.likelyfail.exceptions;

public class SimulatedRuntimeException extends RuntimeException {
    public SimulatedRuntimeException() {
        super("This is a simulated runtime exception");
    }
}
