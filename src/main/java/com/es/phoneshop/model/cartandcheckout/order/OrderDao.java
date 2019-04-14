package com.es.phoneshop.model.cartandcheckout.order;

import com.es.phoneshop.exception.NoElementWithSuchSecureIdException;

public interface OrderDao {
    void save(Order order);
    Order getOrderBySecureId(String secureId) throws NoElementWithSuchSecureIdException;
}
