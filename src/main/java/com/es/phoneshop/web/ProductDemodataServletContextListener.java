package com.es.phoneshop.web;

import com.es.phoneshop.constant.SamplePhoneConstant;
import com.es.phoneshop.model.product.ArrayListProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ProductDemodataServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (servletContextEvent.getServletContext().getInitParameter("InitProductDAOWithConstants").equals("true")){
            ArrayListProductDao products = ArrayListProductDao.getInstance();
            products.save(SamplePhoneConstant.SAMSUNG_GALAXY_S);
            products.save(SamplePhoneConstant.SAMSUNG_GALAXY_S_II);
            products.save(SamplePhoneConstant.SAMSUNG_GALAXY_S_III);
            products.save(SamplePhoneConstant.HTC_EVO_SHIFT_4G);
            products.save(SamplePhoneConstant.IPHONE);
            products.save(SamplePhoneConstant.IPHONE6);
            products.save(SamplePhoneConstant.NOKIA_3310);
            products.save(SamplePhoneConstant.PALM_PIXI);
            products.save(SamplePhoneConstant.SIEMENS_C56);
            products.save(SamplePhoneConstant.SIEMENS_C61);
            products.save(SamplePhoneConstant.SIEMENS_SXG75);
            products.save(SamplePhoneConstant.SONY_ERICSSON_C901);
            products.save(SamplePhoneConstant.Sony_XPERIA_XZ);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //
    }
}
