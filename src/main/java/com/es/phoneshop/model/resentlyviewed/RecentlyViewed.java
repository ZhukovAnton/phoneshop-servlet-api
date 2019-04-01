package com.es.phoneshop.model.resentlyviewed;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecentlyViewed {
    private LinkedList<Product> recentlyViewedItems;

    public List<Product> getRecentlyViewedAsList() {
        return new ArrayList<>(recentlyViewedItems);
    }

    RecentlyViewed() {
        recentlyViewedItems = new LinkedList<>();
    }

    public void add(Product product) {
        recentlyViewedItems.addLast(product);
    }

    public void poll() {
        recentlyViewedItems.poll();
    }

    public int size() {
        return recentlyViewedItems.size();
    }
}
