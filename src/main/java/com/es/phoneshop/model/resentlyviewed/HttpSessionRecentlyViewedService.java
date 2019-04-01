package com.es.phoneshop.model.resentlyviewed;

import com.es.phoneshop.model.product.Product;

public class HttpSessionRecentlyViewedService implements RecentlyViewedService {
    private static volatile HttpSessionRecentlyViewedService instance;
    private RecentlyViewed recentlyViewed;
    private static int amountOfRecentlyViewed;  //Configure with ServletContextListener

    public static HttpSessionRecentlyViewedService getInstance() {
        if (instance == null) {
            synchronized (HttpSessionRecentlyViewedService.class) {
                if (instance == null) {
                    instance = new HttpSessionRecentlyViewedService();
                }
            }
        }
        return instance;
    }

    private HttpSessionRecentlyViewedService() {
        recentlyViewed = new RecentlyViewed();
    }

    @Override
    public RecentlyViewed getRecentlyViewed() {
        return recentlyViewed;
    }

    public static void setAmountOfRecentlyViewed(int amount) {
        amountOfRecentlyViewed = amount;
    }

    @Override
    public void add(Product product) {
        if (recentlyViewed.getRecentlyViewedAsList().contains(product)) return;
        if (recentlyViewed.size() < amountOfRecentlyViewed) {
            recentlyViewed.add(product);
        } else {
            recentlyViewed.poll();
            recentlyViewed.add(product);
        }
    }
}
