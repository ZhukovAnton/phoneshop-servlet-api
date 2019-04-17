package com.es.phoneshop.exception;

public class NameIsRequiredException extends Exception {
    public NameIsRequiredException() {
    }

    public NameIsRequiredException(String message) {
        super(message);
    }

    public NameIsRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameIsRequiredException(Throwable cause) {
        super(cause);
    }
}
