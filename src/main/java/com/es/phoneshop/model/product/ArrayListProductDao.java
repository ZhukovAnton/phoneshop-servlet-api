package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;
import com.es.phoneshop.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Product getProduct(Long id) throws NoSuchProductWithCurrentIdException {
        return products.stream()
                .filter(p -> p.getId().equals(id) && p.getStock() > 0 && p.getPrice() != null)
                .findFirst()
                .orElseThrow(NoSuchProductWithCurrentIdException::new);
    }

    @Override
    public List<Product> findProducts() {
        return products.stream()
                .filter(Product::isValid)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> processRequestForPLP(HttpServletRequest request){
        List<Product> returnList;
        ProductService service = ProductService.getInstance();

        String paramSearch = request.getParameter("search");
        String paramSort = request.getParameter("sort");
        String paramOrder = request.getParameter("order");

        if (paramSearch != null && !paramSearch.isEmpty()){
            returnList = service.searchProducts(paramSearch);
        }
        else{
            returnList = findProducts();
        }
        if (paramSort != null && !paramSort.isEmpty()){
            service.sortProducts(returnList, paramSort, paramOrder);
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
    public void delete(Long id) throws NoSuchProductWithCurrentIdException {
        if (!products.removeIf(p -> p.getId().equals(id))) throw new NoSuchProductWithCurrentIdException();
    }
}