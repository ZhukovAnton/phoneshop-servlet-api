package com.es.phoneshop.web;

import com.es.phoneshop.exception.IllegalSortParametrException;
import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    ArrayListProductDao productDao;

    @Override
    public void init(){
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String URI = request.getRequestURI();
        String productID = URI.substring(URI.lastIndexOf('/') + 1);

        if (!productID.matches("^\\d+$")){
            response.sendError(404);
            return;
        }

        Product product;
        try{
            product = productDao.getProduct(Long.parseLong(productID));
        }
        catch(IllegalSortParametrException | NoSuchProductWithCurrentIdException e){
            response.sendError(404);
            return;
        }


        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);

    }
}
