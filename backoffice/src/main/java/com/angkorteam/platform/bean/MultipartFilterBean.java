package com.angkorteam.platform.bean;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        MultipartResolver multipartResolver = lookupMultipartResolver(request);

        HttpServletRequest processedRequest = request;
        if (multipartResolver.isMultipart(processedRequest)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Resolving multipart request [" + processedRequest.getRequestURI() +
                        "] with MultipartFilter");
            }
            processedRequest = multipartResolver.resolveMultipart(processedRequest);
        } else {
            // A regular request...
            if (logger.isDebugEnabled()) {
                logger.debug("Request [" + processedRequest.getRequestURI() + "] is not a multipart request");
            }
        }

        try {
            byte[] content = IOUtils.toByteArray(processedRequest.getInputStream());
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(processedRequest, content);
            filterChain.doFilter(wrapper, response);
        } finally {
            if (processedRequest instanceof MultipartHttpServletRequest) {
                multipartResolver.cleanupMultipart((MultipartHttpServletRequest) processedRequest);
            }
        }
    }

    public static class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

        private byte[] content;

        public HttpServletRequestWrapper(HttpServletRequest request, byte[] content) {
            super(request);
            this.content = content;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(this.content)));
        }

    }


}
