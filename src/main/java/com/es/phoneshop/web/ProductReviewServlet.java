package com.es.phoneshop.web;

import com.es.phoneshop.exception.CommentIsRequiredException;
import com.es.phoneshop.exception.IllegalRateException;
import com.es.phoneshop.exception.NameIsRequiredException;
import com.es.phoneshop.model.productreview.ProductReview;
import com.es.phoneshop.model.productreview.ProductReviewService;
import com.es.phoneshop.utility.Utility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductReviewServlet extends HttpServlet {
    private ProductReviewService service;


    @Override
    public void init() throws ServletException {
        service = ProductReviewService.getInstance();
    }
    //I don't know why, but it seems that it doesn't enter this servlet when button "Add comment" was pressed. Although
    // I has configured it in web.xml on URL /review/* and button has to send request on this URL...
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productID = 0L;
        try{
            try{
                productID = Utility.getProductIdFromRequest(request);
            }
            catch(NumberFormatException e) {
                response.sendError(404);
            }
            String name = request.getParameter("name");

            int rate = Integer.valueOf(request.getParameter("rate"));

            String comment = request.getParameter("comment");

            if (rate < 1 || rate > 5) {
                throw new IllegalRateException("Rate can be only from 1 to 5");
            }

            if (name == null || name.equals("")) {
                throw new NameIsRequiredException("Name is required");
            }

            if (comment == null || comment.equals("")) {
                throw new CommentIsRequiredException("Comment is required");
            }
            ProductReview review = new ProductReview(name, rate, comment);
            service.addReview(productID, review);
        }
        catch(IllegalRateException e) {
            request.setAttribute("rateError", e.getMessage());
            doGet(request, response);
        }
        catch(NameIsRequiredException e) {
            request.setAttribute("nameError", e.getMessage());
            doGet(request, response);
        }
        catch(CommentIsRequiredException e) {
            request.setAttribute("commentError", e.getMessage());
            doGet(request, response);
        }

        response.sendRedirect(request.getContextPath() + "/products/" + productID);
    }
}
