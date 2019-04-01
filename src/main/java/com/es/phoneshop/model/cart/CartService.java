package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.OutOfStockException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    void addToCart(Cart cart, long productId, int quantity) throws OutOfStockException;

    Cart getCart(HttpServletRequest request);
}
