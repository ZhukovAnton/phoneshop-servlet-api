package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ArrayListProductDao products;

    @Override
    public void init(){
        products = ArrayListProductDao.getInstance();
        products.initWithPhoneConstants();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", products.processRequestForPLP(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}