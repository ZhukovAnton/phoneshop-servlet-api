package com.es.phoneshop.model.cartandcheckout.order;

import com.es.phoneshop.model.cartandcheckout.cart.Cart;
import com.es.phoneshop.model.cartandcheckout.Item;

import java.util.stream.Collectors;

public class HttpSessionOrderService implements OrderService {
    private static volatile HttpSessionOrderService instance;
    private Order order;
    private ArrayListOrderDao orderDao;

    public static HttpSessionOrderService getInstance() {
        if (instance == null) {
            synchronized (HttpSessionOrderService.class) {
                if (instance == null) {
                    instance = new HttpSessionOrderService();
                }
            }
        }
        return instance;
    }

    private HttpSessionOrderService() {
        order = new Order();
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    public Order getOrder(Cart cart) {
        order.setOrderItems(cart.getItems().stream().map(Item::new).collect(Collectors.toList()));
        order.setTotalPrice(cart.getTotalPrice());
        return order;
    }

    @Override
    public void placeOrder(Order order) {
        orderDao.save(order);
    }
}
