package com.es.phoneshop.model.resentlyviewed;

import com.es.phoneshop.model.product.Product;

public interface RecentlyViewedService {
    RecentlyViewed getRecentlyViewed();

    void add(Product product);
}
