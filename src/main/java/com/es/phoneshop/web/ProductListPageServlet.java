package com.es.phoneshop.web;

import com.es.phoneshop.exception.IllegalSortParametrException;
import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.resentlyviewed.HttpSessionRecentlyViewedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;

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
            if (sort != null) {
                if (!sort.equals("description")  && !sort.equals("price") && !sort.equals("")) {
                    throw new IllegalSortParametrException();
                }
            }
            request.setAttribute("products", productDao.findProducts(searchLine, sort, order));
        }
        catch(NoSuchProductWithCurrentIdException | IllegalSortParametrException e){
            response.sendError(404);
            return;
        }
        request.setAttribute("cart", HttpSessionCartService.getInstance().getCart(request).getCartItems());
        request.setAttribute("recentlyViewed", HttpSessionRecentlyViewedService.getInstance()
                                                                                    .getRecentlyViewed()
                                                                                    .getRecentlyViewedAsList());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}