package com.es.phoneshop.exception;

public class CommentIsRequiredException extends Exception {
    public CommentIsRequiredException() {
    }

    public CommentIsRequiredException(String message) {
        super(message);
    }

    public CommentIsRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentIsRequiredException(Throwable cause) {
        super(cause);
    }
}
