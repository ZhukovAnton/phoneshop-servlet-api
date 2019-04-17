package com.es.phoneshop.exception;

public class IllegalRateException extends Exception {
    public IllegalRateException() {
    }

    public IllegalRateException(String message) {
        super(message);
    }

    public IllegalRateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalRateException(Throwable cause) {
        super(cause);
    }
}
