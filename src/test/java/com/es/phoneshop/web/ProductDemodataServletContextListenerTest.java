package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {
    @Mock
    ProductDao productDao;
    @InjectMocks
    ProductDemodataServletContextListener productContextListener;

    @Mock
    ServletContextEvent servletContextEvent;
    @Mock
    ServletContext context;

    @Before
    public void setUp() {
        when(servletContextEvent.getServletContext()).thenReturn(context);
        when(context.getInitParameter("InitProductDAOWithConstants")).thenReturn("true");
    }

    @Test
    public void testContextInitialized() {
        productContextListener.contextInitialized(servletContextEvent);
        verify(productDao, times(13)).save(any(Product.class));
    }
}
