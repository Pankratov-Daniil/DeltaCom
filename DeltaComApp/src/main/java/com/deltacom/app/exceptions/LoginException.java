package com.deltacom.app.exceptions;

/**
 * Exception fires when there was error while working with login service
 */
public class LoginException extends RepositoryException {
    /**
     * Exception gets message and cause object
     * @param message exception message
     * @param cause exception object
     */
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
