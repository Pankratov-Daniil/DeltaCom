package com.deltacom.app.exceptions;

/**
 * Exception fires when there was error while working with tariff services
 */
public class TariffException extends RepositoryException {
    /**
     * Exception gets message and cause object
     * @param message exception message
     * @param cause exception object
     */
    public TariffException(String message, Throwable cause) {
        super(message, cause);
    }
}
