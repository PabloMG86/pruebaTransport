package com.itxiop.transport.domain.exceptions;

/**
 * Exception thrown when a resource is not found
 */
public class ResourceNotFoundException extends DomainException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
