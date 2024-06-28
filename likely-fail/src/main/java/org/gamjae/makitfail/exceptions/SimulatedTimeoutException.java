package org.gamjae.makitfail.exceptions;

import java.util.concurrent.TimeoutException;

public class SimulatedTimeoutException extends TimeoutException {
    public SimulatedTimeoutException() {
        super("This is a simulated timeout exception");
    }
}