package com.itxiop.transport.domain.exceptions;

public class CoreRuntimeException extends RuntimeException {

    public CoreRuntimeException(String message) {
        super(message);
    }

    public CoreRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
