package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.NoMoreSuchItemInCart;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService, Serializable {
    private static final String SESSION_CART_KEY = "sessionCart";
    private static volatile HttpSessionCartService instance;
    private static ProductDao productDao = ArrayListProductDao.getInstance();

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
    public void delete(Cart cart, long productId) throws NoMoreSuchItemInCart {
        Optional<CartItem> cartItemOptional = getOptionalCartItem(cart, productId);
        if (cartItemOptional.isPresent()) {
            cart.getCartItems().removeIf(p -> p.getProduct().getId().equals(productId));
        }
        else {
            throw new NoMoreSuchItemInCart("There is no such Phone anymore. Please, reload page :)");
        }
    }

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
    public void update(Cart cart, long productId, Integer newQuantity) throws OutOfStockException,
                                                                              NoMoreSuchItemInCart,
                                                                              IllegalArgumentException  {

        //need optional because client can open two tabs with cart and than delete item from one of them
        Optional<CartItem> cartItemOptional = getOptionalCartItem(cart, productId);
        if (cartItemOptional.isPresent()) {
            CartItem item = cartItemOptional.get();
            int productStock = item.getProduct().getStock();
            if (newQuantity <= productStock) {
                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                }
                else {
                    throw new IllegalArgumentException("Can't be less than 0");
                }
                updateTotalPrice(cart);
            }
            else {
                throw new OutOfStockException("Not enough stock. Stock is " + productStock);
            }
        }
        else {
            throw new NoMoreSuchItemInCart("There is no such Phone anymore. Please, reload page :)");
        }
    }

    @Override
    public void addToCart(Cart cart, long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);
        int stock = product.getStock();

        Optional<CartItem> optionalItem = cart.getCartItems()
                                                .stream()
                                                .filter(p -> p.getProduct().getId().equals(productId)).findAny();
        if (optionalItem.isPresent()) {
            CartItem item = optionalItem.get();
            if (item.getQuantity() + quantity <= stock) {
                item.setQuantity(item.getQuantity() + quantity);
                updateTotalPrice(cart);
            }
            else {
                throw new OutOfStockException("Not enough stock. Stock is " + stock);
            }
        }
        else {
            if (quantity <= stock) {
                CartItem item = new CartItem(product, quantity);
                cart.getCartItems().add(item);
                updateTotalPrice(cart);
            }
            else {
                throw new OutOfStockException("Not enough stock. Stock is " + stock);
            }
        }
    }

    private Optional<CartItem> getOptionalCartItem(Cart cart, long productId) {
        return cart.getCartItems()
                .stream()
                .filter(p -> p.getProduct()
                                .getId()
                                .equals(productId))
                .findFirst();
    }

    private void updateTotalPrice(Cart cart) {
        cart.setTotalPrice(cart.getCartItems().stream()
                .map(p -> p.getProduct()
                            .getPrice()
                            .multiply(new BigDecimal(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

}
