package com.deltacom.app.exceptions;

public class TariffsWatcherException extends RuntimeException {
    /**
     * Exception with tariffs wather
     * @param message exception message
     * @param cause exception object
     */
    public TariffsWatcherException(String message, Throwable cause) {
        super(message, cause);
    }
}
