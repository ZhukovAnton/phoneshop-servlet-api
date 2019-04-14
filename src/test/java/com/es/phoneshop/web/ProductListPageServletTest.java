package com.es.phoneshop.web;

import com.es.phoneshop.model.cartandcheckout.cart.Cart;
import com.es.phoneshop.model.cartandcheckout.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.resentlyviewed.HttpSessionRecentlyViewedService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private HttpSessionRecentlyViewedService recentlyViewedService;
    @Mock
    private HttpSessionCartService cartService;
    @InjectMocks
    private ProductListPageServlet servlet = new ProductListPageServlet();

    @Mock
    HttpSession session;
    @Mock
    Cart cart;

    @Before
    public void setup(){
        servlet.init();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("sessionCart")).thenReturn(cart);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}