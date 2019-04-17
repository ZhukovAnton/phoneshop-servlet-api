package com.es.phoneshop.model.productreview;

public class ProductReview {
    private String name;
    private int rate;
    private String comment;

    public ProductReview(String name, int rate, String comment) {
        this.name = name;
        this.rate = rate;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
