package com.es.phoneshop.web;

import com.es.phoneshop.exception.IllegalSortParametrException;
import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;
import com.es.phoneshop.model.product.ArrayListProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ArrayListProductDao productDao;

    @Override
    public void init(){
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchLine = request.getParameter("search");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        try{
            request.setAttribute("products", productDao.findProducts(searchLine, sort, order));
        }
        catch(NoSuchProductWithCurrentIdException | IllegalSortParametrException e){
            response.sendError(404);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}