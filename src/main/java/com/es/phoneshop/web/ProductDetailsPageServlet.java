package com.es.phoneshop.web;

import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cartandcheckout.cart.Cart;
import com.es.phoneshop.model.cartandcheckout.cart.CartService;
import com.es.phoneshop.model.cartandcheckout.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.resentlyviewed.HttpSessionRecentlyViewedService;
import com.es.phoneshop.model.resentlyviewed.RecentlyViewedService;
import com.es.phoneshop.utility.Utility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init(){
        cartService = HttpSessionCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long productID;
            productID = Utility.getProductIdFromRequest(request);
            Product product;
            product = productDao.getProduct(productID);
            request.setAttribute("product", product);
            request.setAttribute("recentlyViewed", recentlyViewedService.getRecentlyViewed().getRecentlyViewedAsList());
            recentlyViewedService.add(product);
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        }
        catch(NumberFormatException e) {
            response.sendError(404);
        }
        catch(NoSuchProductWithCurrentIdException e){
            response.sendError(404, "There are no such product");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        long productID = 0L;
        try{
            try{
                productID = Utility.getProductIdFromRequest(request);
            }
            catch(NumberFormatException e) {
                response.sendError(404);
            }
            int quantity;
            quantity = Integer.valueOf(request.getParameter("quantity"));
            if (quantity <= 0) {
                throw new NumberFormatException("Can't be less than 1");
            }
            Cart cart = cartService.getCart(request);
            cartService.addToCart(cart, productID, quantity);
            response.sendRedirect(request.getRequestURI() + "?message=Added Successfully");
        }
        catch(NumberFormatException e){
            request.setAttribute("error", "Not a Number");
            doGet(request, response);
        }
        catch(OutOfStockException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}
