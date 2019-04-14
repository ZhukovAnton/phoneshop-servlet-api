package com.es.phoneshop.model.cartandcheckout.cart;

import com.es.phoneshop.model.cartandcheckout.Item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private BigDecimal totalPrice;

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Cart() {
        items = new ArrayList<>();
    }
}
