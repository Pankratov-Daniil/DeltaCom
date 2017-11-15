package com.deltacom.app.exceptions;

/**
 * Exception fires when there was error while working with client location service
 */
public class ClientLocationException extends RepositoryException {
    /**
     * Exception gets message and cause object
     * @param message exception message
     * @param cause exception object
     */
    public ClientLocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
