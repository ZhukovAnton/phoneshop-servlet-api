package com.es.phoneshop.exception;

public class NoMoreSuchItemInCart extends Exception  {
    public NoMoreSuchItemInCart() {
    }

    public NoMoreSuchItemInCart(String message) {
        super(message);
    }

    public NoMoreSuchItemInCart(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMoreSuchItemInCart(Throwable cause) {
        super(cause);
    }
}
