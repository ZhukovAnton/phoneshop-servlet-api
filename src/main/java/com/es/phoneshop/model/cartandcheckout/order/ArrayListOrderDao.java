package com.es.phoneshop.model.cartandcheckout.order;

import com.es.phoneshop.exception.NoElementWithSuchSecureIdException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArrayListOrderDao implements OrderDao{
    private List<Order> orderDao;
    private AtomicLong orderId;
    private static volatile ArrayListOrderDao instance;

    public static ArrayListOrderDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListOrderDao.class) {
                if (instance == null) {
                    instance = new ArrayListOrderDao();
                }
            }
        }
        return instance;
    }

    private ArrayListOrderDao() {
        orderDao = new ArrayList<>();
        orderId = new AtomicLong(0);
    }

    @Override
    public void save(Order order) {
        order.setId(orderId.incrementAndGet());
        orderDao.add(order);
        order.setSecureId(UUID.randomUUID().toString());
    }

    @Override
    public Order getOrderBySecureId(String secureId) throws NoElementWithSuchSecureIdException{
        try {
            return orderDao.stream().filter(order -> secureId.equals(order.getSecureId())).findAny().get();
        }
        catch(NoSuchElementException e) {
            throw new NoElementWithSuchSecureIdException("oops");
        }
    }
}
