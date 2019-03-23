package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws NoSuchProductWithCurrentIdException;
    List<Product> findProducts(HttpServletRequest request);
    List<Product> findProducts();
    void save(Product product);
    void delete(Long id) throws NoSuchProductWithCurrentIdException;
    void initWithPhoneConstants();
}
