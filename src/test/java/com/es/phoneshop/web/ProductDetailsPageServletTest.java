package com.es.phoneshop.web;


import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
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

    @Before
    public void setUp() {
        when(request.getRequestURI()).thenReturn("/1");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(productDao.getProduct(1L)).thenReturn(product);
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        servlet.doGet(request, response);
        verify(dispatcher).forward(request,response);
    }

    @Test
    public void testSetAttribute() throws IOException, ServletException {
        servlet.doGet(request, response);
        verify(request).setAttribute("product", product);
    }

}
