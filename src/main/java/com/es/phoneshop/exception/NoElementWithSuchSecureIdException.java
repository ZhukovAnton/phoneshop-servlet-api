package com.es.phoneshop.exception;

public class NoElementWithSuchSecureIdException extends Exception {
    public NoElementWithSuchSecureIdException() {
    }

    public NoElementWithSuchSecureIdException(String message) {
        super(message);
    }

    public NoElementWithSuchSecureIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoElementWithSuchSecureIdException(Throwable cause) {
        super(cause);
    }
}
