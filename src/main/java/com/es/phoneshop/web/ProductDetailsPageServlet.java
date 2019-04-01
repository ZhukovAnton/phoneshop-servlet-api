package com.es.phoneshop.web;

import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(){
        cartService = HttpSessionCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productID = 0L;
        try{
            productID = getProductIdFromRequest(request);
        }
        catch(NumberFormatException e) {
            response.sendError(404);
        }

        Product product;
        try{
            product = productDao.getProduct(productID);
        }
        catch(NoSuchProductWithCurrentIdException e){
            response.sendError(404, "There are no such product");
            return;
        }


        request.setAttribute("product", product);
        request.setAttribute("cart", cartService.getCart(request).getCartItems());
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        long productID = 0L;
        try{
            productID = getProductIdFromRequest(request);
        }
        catch(NumberFormatException e) {
            response.sendError(404);
        }

        int quantity;
        try{
            quantity = Integer.valueOf(request.getParameter("quantity"));
            if (quantity <= 0) {
                throw new NumberFormatException();
            }
        }
        catch(NumberFormatException e){
            request.setAttribute("error", "Not a Number");
            doGet(request, response);
            return;
        }
        Cart cart = cartService.getCart(request);
        try{
            cartService.addToCart(cart, productID, quantity);
        }
        catch(OutOfStockException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?message=Added Successfully");
    }

    private Long getProductIdFromRequest(HttpServletRequest request){
        String URI = request.getRequestURI();
        String productId = URI.substring(URI.lastIndexOf('/') + 1);
        return Long.parseLong(productId);
    }
}
