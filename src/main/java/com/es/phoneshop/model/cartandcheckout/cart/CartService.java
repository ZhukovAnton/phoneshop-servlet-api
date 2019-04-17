package com.es.phoneshop.model.cartandcheckout.cart;

import com.es.phoneshop.exception.NoMoreSuchItemInCart;
import com.es.phoneshop.exception.OutOfStockException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    void addToCart(Cart cart, long productId, int quantity) throws OutOfStockException;
    void update(Cart cart, long productId, Integer newQuantity) throws OutOfStockException, NoMoreSuchItemInCart;
    void delete(Cart cart, long productId) throws NoMoreSuchItemInCart;
    void clearCart(Cart cart);
    Cart getCart(HttpServletRequest request);
}
