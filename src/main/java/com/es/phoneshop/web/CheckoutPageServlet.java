package com.es.phoneshop.web;

import com.es.phoneshop.model.cartandcheckout.cart.Cart;
import com.es.phoneshop.model.cartandcheckout.cart.CartService;
import com.es.phoneshop.model.cartandcheckout.cart.HttpSessionCartService;
import com.es.phoneshop.model.cartandcheckout.order.HttpSessionOrderService;
import com.es.phoneshop.model.cartandcheckout.order.Order;
import com.es.phoneshop.model.cartandcheckout.order.OrderService;
import com.es.phoneshop.model.delivery.DeliveryMode;
import com.es.phoneshop.model.delivery.DeliveryModeService;
import com.es.phoneshop.model.delivery.DeliveryModeServiceImpl;
import com.es.phoneshop.model.resentlyviewed.HttpSessionRecentlyViewedService;
import com.es.phoneshop.model.resentlyviewed.RecentlyViewedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CheckoutPageServlet extends HttpServlet {
    private static final String EMPTY_CART_ERROR_MESSAGE =
            "Your cart is empty. Please add something to cart, before checkout";
    private CartService cartService;
    private OrderService orderService;
    private DeliveryModeService deliveryService;
    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        orderService = HttpSessionOrderService.getInstance();
        deliveryService = DeliveryModeServiceImpl.getInstance();
        recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        if (checkIsCartEmpty(request, response, cart)) {return;}
        renderPage(request, response, orderService.getOrder(cart));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String date = request.getParameter("date");
        String paymentMethod = request.getParameter("payment");
        String deliveryModeStr = request.getParameter("delivery");
        DeliveryMode.EnumDelModes deliveryMode = DeliveryMode.getDeliveryModeFromString(deliveryModeStr);


        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        if (checkIsCartEmpty(request, response, cart)) {return;}

        boolean hasErrors = false;

        if (name == null || name.equals("")) {
            hasErrors = true;
            request.setAttribute("nameError", "Name is required");
        }

        if (address == null || address.equals("")) {
            hasErrors = true;
            request.setAttribute("addressError", "Address is required");
        }

        if (date == null || date.equals("")) {
            hasErrors = true;
            request.setAttribute("dateError", "Date is required");
        }

        if (hasErrors) {
            renderPage(request, response, order);
            return;
        }

        order.setName(name);
        order.setAddress(address);
        order.setDate(date);
        order.setPaymentMethod(paymentMethod);
        order.setDeliveryMode(deliveryMode);
        order.setTotalPrice(order.getTotalPrice().add(deliveryMode.getDeliveryCost()));
        orderService.placeOrder(order);
        cartService.clearCart(cart);
        response.sendRedirect(request.getRequestURI() + "/overview/" + order.getSecureId());
    }

    private void renderPage(HttpServletRequest request, HttpServletResponse response, Order order)
            throws ServletException,
                   IOException {
        request.setAttribute("order", order);
        request.setAttribute("delivery", deliveryService.getDeliveryMode());
        request.setAttribute("recentlyViewed", recentlyViewedService.getRecentlyViewed().getRecentlyViewedAsList());
        request.getRequestDispatcher("WEB-INF/pages/order.jsp").forward(request, response);
    }

    private boolean checkIsCartEmpty(HttpServletRequest request, HttpServletResponse response, Cart cart)
            throws IOException {
        if (cart.isEmpty()) {
            request.setAttribute("errors", new String[] {EMPTY_CART_ERROR_MESSAGE});
            response.sendRedirect(request.getContextPath() + "/cart");
            return true;
        }
        else {
            return false;
        }
    }

}
