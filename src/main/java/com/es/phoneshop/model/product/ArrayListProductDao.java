package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;
import com.es.phoneshop.parser.SearchLineParser;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;

    public ArrayListProductDao() {
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
    public List<Product> findProducts(HttpServletRequest request) {
        List<Product> returnList;
        String param = request.getParameter("query");
        if (param == null || param.isEmpty()){
            returnList = findProducts();
        }
        else {
            String[] words = SearchLineParser.getInstance().parseLine(param);
            returnList = products.stream()
                    .filter(Product::isValid)
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

    @Override
    public void initWithPhoneConstants() {
        save(SamplePhoneConstant.SAMSUNG_GALAXY_S);
        save(SamplePhoneConstant.SAMSUNG_GALAXY_S_II);
        save(SamplePhoneConstant.SAMSUNG_GALAXY_S_III);
        save(SamplePhoneConstant.HTC_EVO_SHIFT_4G);
        save(SamplePhoneConstant.IPHONE);
        save(SamplePhoneConstant.IPHONE6);
        save(SamplePhoneConstant.NOKIA_3310);
        save(SamplePhoneConstant.PALM_PIXI);
        save(SamplePhoneConstant.SIEMENS_C56);
        save(SamplePhoneConstant.SIEMENS_C61);
        save(SamplePhoneConstant.SIEMENS_SXG75);
        save(SamplePhoneConstant.SONY_ERICSSON_C901);
        save(SamplePhoneConstant.Sony_XPERIA_XZ);
    }
}