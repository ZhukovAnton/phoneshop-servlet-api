package com.es.phoneshop.service;

import com.es.phoneshop.exception.ComparatorGenerationException;
import com.es.phoneshop.model.product.Product;

import java.util.Comparator;

public class ComparatorGenerator {
    private static volatile ComparatorGenerator instance;

    public static ComparatorGenerator getInstance() {
        ComparatorGenerator localInstance = instance;
        if (localInstance == null){
            synchronized (ComparatorGenerator.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new ComparatorGenerator();
                }
            }
        }
        return localInstance;
    }


    public Comparator<Product> getComparator(String sort, String order) {
        final String ASC = "asc";
        final String DESC = "desc";
        switch(sort){
            case "description":
                switch (order) {
                    case ASC:
                        return (Product first, Product second) -> {
                            return first.getDescription().compareTo(second.getDescription());
                        };
                    case DESC:
                        return (Product first, Product second) -> {
                            return second.getDescription().compareTo(first.getDescription());
                        };
                    default:
                        throw new ComparatorGenerationException();
                }
            case "price" :
                switch (order) {
                    case ASC:
                        return (Product first, Product second) -> {
                            return first.getPrice().compareTo(second.getPrice());
                        };
                    case DESC:
                        return (Product first, Product second) -> {
                            return second.getPrice().compareTo(first.getPrice());
                        };
                    default:
                        throw new ComparatorGenerationException();
                }
             default: throw new ComparatorGenerationException();
        }
    }

}
