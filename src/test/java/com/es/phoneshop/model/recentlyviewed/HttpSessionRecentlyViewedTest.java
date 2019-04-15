package com.es.phoneshop.model.recentlyviewed;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.resentlyviewed.HttpSessionRecentlyViewedService;
import com.es.phoneshop.model.resentlyviewed.RecentlyViewed;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionRecentlyViewedTest {
    private static final int AMOUNT_OF_RECENTLY_VIEWED = 3;
    @Mock
    private RecentlyViewed recentlyViewed;
    @InjectMocks
    private static HttpSessionRecentlyViewedService service;

    private Product product1 = new Product();
    private Product product2 = new Product();
    private Product product3 = new Product();
    private Product product4 = new Product();



    @Before
    public void setUp() {
        product1.setCode("1");
        product2.setCode("2");
        product3.setCode("3");
        product4.setCode("4");
        product1.setId(1L);
        product2.setId(2L);
        product3.setId(3L);
        product4.setId(4L);
        HttpSessionRecentlyViewedService.setAmountOfRecentlyViewed(AMOUNT_OF_RECENTLY_VIEWED);
    }

    @Test
    public void addTestWithoutOverwriting() {
        service.add(product1);
        verify(recentlyViewed).add(product1);
    }

    @Test
    public void addTestWithOverWriting() {
        service.add(product1);
        service.add(product2);
        service.add(product3);
        when(recentlyViewed.size()).thenReturn(3);
        service.add(product4);
        verify(recentlyViewed).poll();
        verify(recentlyViewed).add(product4);
    }

}
