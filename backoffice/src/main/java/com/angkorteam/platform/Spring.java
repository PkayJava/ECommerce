package com.angkorteam.platform;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created by socheatkhauv on 29/1/17.
 */
public class Spring {

    private static ServletContext servletContext;

    public static <T> T getBean(String name, Class<T> requiredType) {
        if (servletContext == null) {
            servletContext = WebApplication.get().getServletContext();
        }
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        return applicationContext.getBean(name, requiredType);
    }


    public static <T> T getBean(Class<T> requiredType) {
        if (servletContext == null) {
            servletContext = WebApplication.get().getServletContext();
        }
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        return applicationContext.getBean(requiredType);
    }

}
