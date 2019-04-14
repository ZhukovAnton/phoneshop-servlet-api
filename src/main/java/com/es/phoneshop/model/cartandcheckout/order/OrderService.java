package com.es.phoneshop.model.cartandcheckout.order;

import com.es.phoneshop.model.cartandcheckout.cart.Cart;

public interface OrderService {
    Order getOrder(Cart cart);
    void placeOrder(Order order);
}
