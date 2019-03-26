package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.IllegalSortParametrException;
import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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




    private Stream<Product> getValidProductStream() {
        return products.stream().filter(p -> p.getPrice() != null && p.getStock() > 0);
    }




    private List<Product> getSearchedProductList(String search) {
        ConcurrentHashMap<Product, Long> concurrentHashMap = new ConcurrentHashMap<>();
        String[] tokens = search.toLowerCase().split("\\s+");
        getValidProductStream().forEach((Product p) -> concurrentHashMap.put(p, Arrays.stream(tokens)
                                            .filter(word -> p.getDescription()
                                                            .toLowerCase()
                                                            .contains(word.toLowerCase()))
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
     * @param listForSorting forced evil for improving getSearchedAndSortedProductList method
     * @param sort
     * @param order
     * @return
     * @throws IllegalSortParametrException
     */

    private List<Product> getSortedProductList(List<Product> listForSorting, String sort, String order) throws IllegalSortParametrException {
        //order - asc by default
        if ((sort.matches("description") || sort.matches("price"))) {
            Comparator<Product> comparator = getComparator(sort);
            if ("desc".equals(order)) comparator = comparator.reversed();
            return listForSorting.stream().sorted(comparator).collect(Collectors.toList());
        }
        else {
            throw new IllegalSortParametrException();
        }
    }



    private List<Product> getSearchedAndSortedProductList(String search, String sort, String order) throws IllegalSortParametrException {
        return getSortedProductList(getSearchedProductList(search), sort, order);
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
        if (null != search && null == sort){
            returnList = getSearchedProductList(search);
        }
        else {
            if (null == search && null != sort) {
                returnList =  getSortedProductList(getValidProductStream().collect(Collectors.toList()), sort, order);
            }
            else{
                if (null != search) {
                    returnList = getSearchedAndSortedProductList(search, sort, order);
                }
            }
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