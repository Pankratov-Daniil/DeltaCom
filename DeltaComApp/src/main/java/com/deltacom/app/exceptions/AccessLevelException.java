package com.deltacom.app.exceptions;

/**
 * Exception fires when there was error while working with access level services
 */
public class AccessLevelException extends RepositoryException {
    /**
     * Exception gets message and cause object
     * @param message exception message
     * @param cause exception object
     */
    public AccessLevelException(String message, Throwable cause) {
        super(message, cause);
    }
}
