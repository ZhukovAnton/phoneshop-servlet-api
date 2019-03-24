package com.es.phoneshop.service;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.parser.SearchLineParser;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static volatile ProductService instance;
    private ArrayListProductDao products = ArrayListProductDao.getInstance();

    public static ProductService getInstance() {
        ProductService localInstance = instance;
        if (localInstance == null) {
            synchronized (ProductService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ProductService();
                }
            }
        }
        return localInstance;
    }

    private ProductService() {}

    public List<Product> searchProducts(String paramSearch) {
        String[] words = SearchLineParser.getInstance().parseLine(paramSearch);

        return products.findProducts()
                .stream()
                .filter(p -> {
                    for (String word : words){
                        if (p.getDescription().toLowerCase().contains(word.toLowerCase())){
                            return true;
                        }
                    }
                    return false;
                })
                .sorted((first, second) -> {
                    Integer amountOfMatchesFirst = 0;
                    Integer amountOfMatchesSecond = 0;
                    for (String word : words){
                        if (first.getDescription().toLowerCase().contains(word.toLowerCase())){
                            amountOfMatchesFirst++;
                        }
                        if (second.getDescription().toLowerCase().contains(word.toLowerCase())){
                            amountOfMatchesSecond++;
                        }
                    }
                    return amountOfMatchesSecond.compareTo(amountOfMatchesFirst);
                })
                .collect(Collectors.toList());
    }

    public void sortProducts(List<Product> products, String paramSort, String paramOrder){
        Comparator<Product> comparator = ComparatorGenerator.getInstance().getComparator(paramSort, paramOrder);
        products.sort(comparator);
    }


}
