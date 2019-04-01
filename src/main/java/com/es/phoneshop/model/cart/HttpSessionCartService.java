package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Optional;

public class HttpSessionCartService implements CartService, Serializable {
    private static final String SESSION_CART_KEY = "sessionCart";
    private static volatile HttpSessionCartService instance;

    public static HttpSessionCartService getInstance() {
        if (instance == null) {
            synchronized (HttpSessionCartService.class) {
                if (instance == null) {
                    instance = new HttpSessionCartService();
                }
            }
        }
        return instance;
    }

    private HttpSessionCartService() {}

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(SESSION_CART_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(SESSION_CART_KEY, cart);
        }
        return cart;
    }

    @Override
    public void addToCart(Cart cart, long productId, int quantity) throws OutOfStockException {
        ProductDao productDao = ArrayListProductDao.getInstance();
        Product product = productDao.getProduct(productId);
        int stock = product.getStock();

        Optional<CartItem> optionalItem = cart.getCartItems()
                                                .stream()
                                                .filter(p -> p.getProduct().getId().equals(productId)).findAny();
        if (optionalItem.isPresent()) {
            CartItem item = optionalItem.get();
            if (item.getQuantity() + quantity <= stock) {
                item.setQuantity(item.getQuantity() + quantity);
            }
            else {
                throw new OutOfStockException("Not enough stock. Stock is " + stock);
            }
        }
        else {
            if (quantity <= stock) {
                CartItem item = new CartItem(product, quantity);
                cart.getCartItems().add(item);
            }
            else {
                throw new OutOfStockException("Not enough stock. Stock is " + stock);
            }
        }
    }


}
