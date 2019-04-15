package com.es.phoneshop.model.cartandcheckout.order;


import com.es.phoneshop.model.cartandcheckout.Item;
import com.es.phoneshop.model.delivery.DeliveryMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private BigDecimal totalPrice;
    private List<Item> orderItems;
    private String name;
    private String address;
    private DeliveryMode.EnumDelModes deliveryMode;
    private Long id;
    private String secureId;

    public Order() {
        orderItems = new ArrayList<>();
    }

    void setOrderItems(List<Item> items) {
        orderItems = items;
    }

    public void setTotalPrice(BigDecimal price) {
        totalPrice = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDeliveryMode(DeliveryMode.EnumDelModes deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecureId() {
        return secureId;
    }

    void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public Long getId() {
        return id;
    }

    public List<Item> getOrderItems() {
        return orderItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public DeliveryMode.EnumDelModes getDeliveryMode() {
        return deliveryMode;
    }

}
