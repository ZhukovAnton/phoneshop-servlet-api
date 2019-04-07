package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.IllegalSortParametrException;
import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;

import java.io.Serializable;
import java.util.List;

public interface ProductDao {
    Product getProduct(long id) throws NoSuchProductWithCurrentIdException;
    List<Product> findProducts(String search, String sort, String order) throws IllegalSortParametrException;
    void save(Product product);
    void delete(long id) throws NoSuchProductWithCurrentIdException;
}
