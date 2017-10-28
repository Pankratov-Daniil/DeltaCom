package com.deltacom.app.exceptions;

/**
 * Exception fires when there was error while working with repository
 */
public class RepositoryException extends RuntimeException {
    /**
     * Exception gets message and cause object
     * @param message exception message
     * @param cause exception object
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
