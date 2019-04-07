package com.es.phoneshop.utility;

import javax.servlet.http.HttpServletRequest;

public class Utility {
    private static volatile Utility instance;

    private Utility() {

    }

    public static Utility getInstance() {
        if (instance == null) {
            synchronized (Utility.class) {
                if (instance == null) {
                    instance = new Utility();
                }
            }
        }
        return instance;
    }

    public static Long getProductIdFromRequest(HttpServletRequest request) throws NumberFormatException {
        String URI = request.getRequestURI();
        String productId = URI.substring(URI.lastIndexOf('/') + 1);
        return Long.parseLong(productId);
    }
}
