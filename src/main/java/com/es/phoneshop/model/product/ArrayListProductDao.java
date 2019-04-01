package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.IllegalSortParametrException;
import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private static volatile ArrayListProductDao instance;
    private List<Product> products;

    public static ArrayListProductDao getInstance() {
        ArrayListProductDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ArrayListProductDao();
                }
            }
        }
        return localInstance;
    }

    private ArrayListProductDao() {
        products = new ArrayList<>();
    }


    private Predicate<Product> getValidationPredicate() {
        return p -> p.getStock() > 0 && null != p.getPrice();
    }


    ArrayListProductDao(List<Product> products) {
        this.products = products;
    }


    private List<Product> getSearchedProductList(String search) {
        ConcurrentHashMap<Product, Long> concurrentHashMap = new ConcurrentHashMap<>();
        String[] tokens = search.toLowerCase().split("\\s+");
        products.stream()
                .filter(getValidationPredicate())
                .forEach((Product p) -> concurrentHashMap.put(p, Arrays.stream(tokens)
                                                                       .filter(word -> p.getDescription()
                                                                                        .toLowerCase()
                                                                                        .contains(word))
                                                                       .count())
        );
        return concurrentHashMap.entrySet().stream()
                .filter((Map.Entry<Product, Long> p) -> p.getValue() > 0)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }




    private Comparator<Product> getComparator(String sort) {
        switch (sort) {
            case "description" : return Comparator.comparing(Product::getDescription);
            case "price" : return Comparator.comparing(Product::getPrice);
            default : return Comparator.comparing(Product::getId);
        }
    }


    /**
     *
     * @param listForSorting - this list will be sorted
     * @param sort - by what field list will be sorted
     * @param order - in what order it will be sorted
     * @return - sorted list
     */

    private List<Product> getSortedProductList(List<Product> listForSorting, String sort, String order) {
        //order - asc by default
        Comparator<Product> comparator = getComparator(sort);
        if ("desc".equals(order)) comparator = comparator.reversed();
        return listForSorting.stream().sorted(comparator).collect(Collectors.toList());
    }



    @Override
    public Product getProduct(long id) throws NoSuchProductWithCurrentIdException {
        return products.stream()
                .filter(p -> p.getId().equals(id) && p.getStock() > 0 && p.getPrice() != null)
                .findFirst()
                .orElseThrow(NoSuchProductWithCurrentIdException::new);
    }

    @Override
    public List<Product> findProducts(String search, String sort, String order) throws IllegalSortParametrException {
        List<Product> returnList = products;
        if (null != search){
            returnList = getSearchedProductList(search);
        }

        if (sort != null) {
            returnList = getSortedProductList(returnList, sort, order);
        }

        return returnList;
    }

    @Override
    public void save(Product product) {
        if (products.stream().noneMatch(p -> p.getId().equals(product.getId()))) {
            products.add(product);
        }
    }

    @Override
    public void delete(long id) throws NoSuchProductWithCurrentIdException {
        if (!products.removeIf(p -> p.getId().equals(id))) throw new NoSuchProductWithCurrentIdException();
    }
}