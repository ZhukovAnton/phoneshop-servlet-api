package com.es.phoneshop.model.cartandcheckout;

import java.io.Serializable;
import java.lang.*;

import com.es.phoneshop.model.product.Product;

public class Item implements Serializable {
    private Product product;
    private int quantity;

    public Item(Item item) {
        this.product = item.getProduct();
        this.quantity = item.getQuantity();
    }

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
