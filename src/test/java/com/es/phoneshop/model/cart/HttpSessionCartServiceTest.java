package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    ProductDao productDao;
    @InjectMocks
    private HttpSessionCartService service;
    private static final String SESSION_CART_KEY = "sessionCart";


    @Mock
    HttpServletRequest request;
    @Mock
    Product product;
    @Mock
    HttpSession session;
    @Mock
    Cart cart;
    @Mock
    CartItem cartItem;

    @Before
    public void setUp() {
        when(product.getStock()).thenReturn(1);
        when(product.getPrice()).thenReturn(new BigDecimal(1));
        when(product.getId()).thenReturn(1L);
        when(request.getSession()).thenReturn(session);
        when(cartItem.getProduct()).thenReturn(product);
        when(cart.getCartItems()).thenReturn(Arrays.asList(cartItem));
    }

    @Test
    public void testGetCartWhenCartIsNotInitialized() {
        service.getCart(request);
        verify(session).setAttribute(eq(SESSION_CART_KEY),any(Cart.class));
    }

    @Test
    public void testGetCartWhenCartIsInitialized() {
        when(session.getAttribute(SESSION_CART_KEY)).thenReturn(cart);
        Assert.assertEquals(service.getCart(request), cart);
    }

    //mock of getProduct() is not working correctly
    //TODO: fix that
    @Test(expected = OutOfStockException.class)
    public void testAddToCart() throws OutOfStockException{
        when(productDao.getProduct(1L)).thenReturn(product);
        when(product.getStock()).thenReturn(1);
        when(cartItem.getQuantity()).thenReturn(1);
        service.addToCart(cart, 1L, 1);
    }

}
