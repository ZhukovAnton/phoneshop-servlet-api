package com.es.phoneshop.web;

import com.es.phoneshop.exception.NoMoreSuchItemInCart;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.resentlyviewed.HttpSessionRecentlyViewedService;
import com.es.phoneshop.model.resentlyviewed.RecentlyViewedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;
    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init(){
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("recentlyViewed", recentlyViewedService.getRecentlyViewed().getRecentlyViewedAsList());
        request.getRequestDispatcher("WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String[] productIDs = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        if (Objects.isNull(productIDs)) {
            doGet(request, response);
            return;
        }
        Cart cart = cartService.getCart(request);
        String[] errors = new String[productIDs.length];
        for (int i = 0; i < productIDs.length; ++i){
             long currentId = Long.parseLong(productIDs[i]);
             Integer newQuantity = parseQuantity(quantities[i], errors, i);
             if (newQuantity != null) {
                 try{
                     cartService.update(cart, currentId, newQuantity);
                 }
                 catch(NoMoreSuchItemInCart | OutOfStockException  e) {
                     errors[i] = e.getMessage();
                 }
                 catch(IllegalArgumentException e) {
                     errors[i] = "Illegal input";
                 }
             }
        }
        boolean hasAnyErrors = Arrays.stream(errors).anyMatch(Objects::nonNull);
        if (hasAnyErrors) {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
        else {
            response.sendRedirect(request.getRequestURI() + "?message=Updated Successfully");
        }
    }

    private Integer parseQuantity(String stringQuantity, String[] errors, int quantityIndex) {
        try{
            return Integer.parseInt(stringQuantity);
        }
        catch(NumberFormatException e) {
            errors[quantityIndex] = "Not a number";
        }
        catch(IllegalArgumentException e) {
            errors[quantityIndex] = "Invalid input";
        }
        return null;
    }
}

