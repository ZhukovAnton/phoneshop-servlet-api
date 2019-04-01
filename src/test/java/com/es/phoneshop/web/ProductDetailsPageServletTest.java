package com.es.phoneshop.web;


import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.resentlyviewed.RecentlyViewed;
import com.es.phoneshop.model.resentlyviewed.RecentlyViewedService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private ProductDao productDao;
    @InjectMocks
    private ProductDetailsPageServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private  Product product;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private List<CartItem> cartItems;
    @Mock
    private RecentlyViewed recentlyViewed;
    @Mock
    private RecentlyViewedService recentlyViewedService;
    @Mock
    private List<Product> recentlyViewedList;

    @Before
    public void setUp() {
        when(request.getRequestURI()).thenReturn("/1");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(productDao.getProduct(1L)).thenReturn(product);
        when(cartService.getCart(request)).thenReturn(cart);
        when(cart.getCartItems()).thenReturn(cartItems);
        when(recentlyViewedService.getRecentlyViewed()).thenReturn(recentlyViewed);
        when(recentlyViewed.getRecentlyViewedAsList()).thenReturn(recentlyViewedList);

    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        servlet.doGet(request, response);
        verify(dispatcher).forward(request,response);
    }

    @Test
    public void testSetProductAttribute() throws IOException, ServletException {
        servlet.doGet(request, response);
        verify(request).setAttribute("product", product);
    }

    @Test
    public void testSetCartAttribute() throws IOException, ServletException {
        servlet.doPost(request, response);
        verify(request).setAttribute("cart", cartService.getCart(request).getCartItems());
    }

    @Test
    public void testSetRecentlyViewedAttribute() throws IOException, ServletException {
        servlet.doGet(request, response);
        verify(request).setAttribute("recentlyViewed", recentlyViewedService.getRecentlyViewed().getRecentlyViewedAsList());
    }
}
