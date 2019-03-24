package com.es.phoneshop.exception;

public class ComparatorGenerationException extends RuntimeException {
    public ComparatorGenerationException() {
    }

    public ComparatorGenerationException(String message) {
        super(message);
    }

    public ComparatorGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComparatorGenerationException(Throwable cause) {
        super(cause);
    }
}
