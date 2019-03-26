package com.es.phoneshop.exception;

public class IllegalSortParametrException extends RuntimeException {
    public IllegalSortParametrException() {
    }

    public IllegalSortParametrException(String message) {
        super(message);
    }

    public IllegalSortParametrException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalSortParametrException(Throwable cause) {
        super(cause);
    }
}
