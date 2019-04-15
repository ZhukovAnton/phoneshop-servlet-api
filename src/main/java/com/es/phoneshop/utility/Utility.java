package com.es.phoneshop.utility;

import javax.servlet.http.HttpServletRequest;

public class Utility {
    private Utility() {
    }

    public static Long getProductIdFromRequest(HttpServletRequest request) throws NumberFormatException {
        String URI = request.getRequestURI();
        String productId = URI.substring(URI.lastIndexOf('/') + 1);
        return Long.parseLong(productId);
    }
}
