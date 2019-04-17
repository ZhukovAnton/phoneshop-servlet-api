package com.es.phoneshop.model.cartandcheckout.cart;

import com.es.phoneshop.exception.NoMoreSuchItemInCart;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cartandcheckout.Item;
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
    private ProductDao productDao = ArrayListProductDao.getInstance();

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
        Optional<Item> cartItemOptional = getOptionalCartItem(cart, productId);
        if (cartItemOptional.isPresent()) {
            cart.getItems().removeIf(p -> p.getProduct().getId().equals(productId));
            updateTotalPrice(cart);
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

        //need optional because client can open two tabs with cart and than delete Item from one of them
        Optional<Item> cartItemOptional = getOptionalCartItem(cart, productId);
        if (cartItemOptional.isPresent()) {
            Item item = cartItemOptional.get();
            int productStock = item.getProduct().getStock();
            if (newQuantity <= productStock) {
                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                }
                else {
                    throw new IllegalArgumentException("Can't be less than 1");
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

        Optional<Item> optionalItem = cart.getItems()
                                                .stream()
                                                .filter(p -> p.getProduct().getId().equals(productId)).findAny();
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
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
                Item item = new Item(product, quantity);
                cart.getItems().add(item);
                updateTotalPrice(cart);
            }
            else {
                throw new OutOfStockException("Not enough stock. Stock is " + stock);
            }
        }
    }

    @Override
    public void clearCart(Cart cart) {
        //TODO:set new empty cart
        cart .getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
    }

    private Optional<Item> getOptionalCartItem(Cart cart, long productId) {
        return cart.getItems()
                .stream()
                .filter(p -> p.getProduct()
                                .getId()
                                .equals(productId))
                .findFirst();
    }

    private void updateTotalPrice(Cart cart) {
        cart.setTotalPrice(cart.getItems().stream()
                .map(p -> p.getProduct()
                            .getPrice()
                            .multiply(new BigDecimal(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

}
