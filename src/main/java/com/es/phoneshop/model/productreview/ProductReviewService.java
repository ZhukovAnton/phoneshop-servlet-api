package com.es.phoneshop.model.productreview;

import com.es.phoneshop.model.resentlyviewed.RecentlyViewed;

import java.util.List;

public class ProductReviewService {
    private static volatile ProductReviewService instance;
    private ProductReviewDao reviewDao;

    public static ProductReviewService getInstance() {
        if (instance == null){
            synchronized (ProductReviewService.class) {
                if (instance == null) {
                    instance = new ProductReviewService();
                }
            }
        }
        return instance;
    }

    private ProductReviewService() {
        reviewDao = ProductReviewDao.getInstance();
    }

    public void addReview(Long id, ProductReview review) {
        reviewDao.saveReview(id, review);
    }

    public List<ProductReview> getReviewsById(Long id) {
        return reviewDao.getProductReviews().get(id);
    }
}
