package com.deltacom.app.exceptions;

/**
 * Exception fires when there was error while working with options services
 */
public class OptionException extends RepositoryException {
    /**
     * Exception gets message and cause object
     * @param message exception message
     * @param cause exception object
     */
    public OptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
