package com.deltacom.app.exceptions;

/**
 * Exception fires when there was error while working with numbers pool services
 */
public class NumbersPoolException extends RepositoryException {
    /**
     * Exception gets message and cause object
     * @param message exception message
     * @param cause exception object
     */
    public NumbersPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
