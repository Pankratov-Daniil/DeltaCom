package com.deltacom.app.exceptions;

/**
 * Exception fires when there was error while working with contract services
 */
public class ContractException extends RepositoryException {
    /**
     * Exception gets message and cause object
     * @param message exception message
     * @param cause exception object
     */
    public ContractException(String message, Throwable cause) {
        super(message, cause);
    }
}
