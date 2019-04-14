package com.es.phoneshop.web;

import com.es.phoneshop.exception.NoMoreSuchItemInCart;
import com.es.phoneshop.model.cartandcheckout.cart.Cart;
import com.es.phoneshop.model.cartandcheckout.cart.CartService;
import com.es.phoneshop.model.cartandcheckout.cart.HttpSessionCartService;
import com.es.phoneshop.utility.Utility;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartDeleteServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cart cart = cartService.getCart(request);
        Long productId = Utility.getProductIdFromRequest(request);
        try{
            cartService.delete(cart, productId);
        }
        catch(NoMoreSuchItemInCart e) {
            request.setAttribute("error", "There is no this Phone in the cart anymore. Please, reload the page :)");
        }
        response.sendRedirect(request.getContextPath() + "/cart?message=Cart Item deleted successfully");
    }
}
