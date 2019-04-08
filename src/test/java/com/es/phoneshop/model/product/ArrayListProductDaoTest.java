package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    @Mock
    private Product product1;
    @Mock
    private Product product2;

    private String search;
    private String sort;
    private String order;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao(new ArrayList<>());

        when(product1.getId()).thenReturn(1L);
        when(product1.getPrice()).thenReturn(new BigDecimal(10));
        when(product1.getStock()).thenReturn(1);
        when(product1.getDescription()).thenReturn("Product1");
        when(product2.getId()).thenReturn(2L);
        when(product2.getPrice()).thenReturn(new BigDecimal(20));
        when(product2.getStock()).thenReturn(2);
        when(product2.getDescription()).thenReturn("Product2");

        productDao.save(product1);
    }

    @Test
    public void deleteTest() {
        try{
            productDao.delete(1L);
        }
        catch(NoSuchProductWithCurrentIdException e){
            e.printStackTrace();
        }
        assertTrue(productDao.findProducts(search, sort, order).isEmpty());
    }

    @Test
    public void saveTest() {
        productDao.save(product2);
        assertEquals(Arrays.asList(product1, product2), productDao.findProducts(search, sort, order));
    }

    @Test
    public void getFromIdTest() {
        productDao.save(product1);
        assertEquals(product1, productDao.getProduct(1L));
    }

    @Test
    public void testFindProductsNoResults() {
        search = "Product2";
        assertTrue(productDao.findProducts(search, sort, order).isEmpty());
    }

    @Test
    public void testFindSortedByDescriptionWithoutOrderProductsSearchResult() {
        search = "Product2 1";
        sort = "Description";
        order = null;
        productDao.save(product2);
        assertEquals(new ArrayList<>(Arrays.asList(product1, product2)), productDao.findProducts(search, sort, order));
    }

    @Test
    public void testFindSortedByDescriptionWithDescOrderProductsSearchResult() {
        search = "Product2 1";
        sort = "Description";
        order = "desc";
        productDao.save(product2);
        assertEquals(new ArrayList<>(Arrays.asList(product2, product1)), productDao.findProducts(search, sort, order));
    }

    @Test
    public void testFindSortedByPriceProducts() {
        search = null;
        sort = "Price";
        order = null;
        productDao.save(product2);
        assertEquals(new ArrayList<>(Arrays.asList(product1, product2)), productDao.findProducts(search, sort, order));
    }


}