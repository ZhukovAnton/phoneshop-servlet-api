package com.es.phoneshop.model.productreview;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ProductReviewDao {
    private Map<Long, ArrayList<ProductReview>> productReviews;
    private static volatile ProductReviewDao instance;

    public static ProductReviewDao getInstance() {
        if (instance == null){
            synchronized (ProductReviewDao.class) {
                if (instance == null) {
                    instance = new ProductReviewDao();
                }
            }
        }
        return instance;
    }

    private ProductReviewDao() {
        productReviews = new ConcurrentHashMap<>();
    }

    void saveReview(Long id, ProductReview review) {
        productReviews.get(id).add(review);
    }

    //TODO: remove by comment id
    public void delete(Long productId, Long commentId) {
        productReviews.remove(productId);
    }

    Map<Long, ArrayList<ProductReview>> getProductReviews() {
        return productReviews;
    }

}
