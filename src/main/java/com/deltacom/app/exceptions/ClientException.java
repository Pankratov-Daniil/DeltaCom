package com.deltacom.app.exceptions;

/**
 * Exception fires when there was error while working with client services
 */
public class ClientException extends RepositoryException {
    /**
     * Exception gets message and cause object
     * @param message exception message
     * @param cause exception object
     */
    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
