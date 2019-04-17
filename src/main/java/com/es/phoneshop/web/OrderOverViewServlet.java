package com.es.phoneshop.web;

import com.es.phoneshop.exception.NoElementWithSuchSecureIdException;
import com.es.phoneshop.model.cartandcheckout.order.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverViewServlet extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Order order = orderDao.getOrderBySecureId(extractSecureId(request));
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/pages/overview.jsp").forward(request, response);
        }
        catch(NoElementWithSuchSecureIdException e) {
            response.sendError(404, e.getMessage());
        }
    }

    private String extractSecureId(HttpServletRequest request) {
        return request.getPathInfo().replaceAll("/", "");
    }

}
