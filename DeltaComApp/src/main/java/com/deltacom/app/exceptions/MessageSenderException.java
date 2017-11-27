package com.deltacom.app.exceptions;

public class MessageSenderException extends RuntimeException {
    /**
     * Exception while sending message
     * @param message exception message
     * @param cause exception object
     */
    public MessageSenderException(String message, Throwable cause) {
        super(message, cause);
    }
}
