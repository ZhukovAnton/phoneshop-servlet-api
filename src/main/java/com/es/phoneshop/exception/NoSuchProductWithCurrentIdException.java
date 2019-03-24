package com.es.phoneshop.exception;

public class NoSuchProductWithCurrentIdException extends RuntimeException {
    public NoSuchProductWithCurrentIdException() {
    }

    public NoSuchProductWithCurrentIdException(String message) {
        super(message);
    }

    public NoSuchProductWithCurrentIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchProductWithCurrentIdException(Throwable cause) {
        super(cause);
    }
}
