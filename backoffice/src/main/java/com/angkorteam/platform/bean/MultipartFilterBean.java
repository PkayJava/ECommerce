package com.angkorteam.platform.bean;

import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by socheat on 8/28/16.
 */
public class MultipartFilterBean extends org.springframework.web.multipart.support.MultipartFilter {

    private MultipartResolver resolver;

    @Override
    protected void initFilterBean() throws ServletException {
        this.resolver = new CommonsMultipartResolver(getServletContext());
    }

    @Override
    protected MultipartResolver lookupMultipartResolver(HttpServletRequest request) {
        return this.resolver;
    }

}
