package com.springapp.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class AuthRequestWrapper extends HttpServletRequestWrapper {
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private final HttpServletRequest request;

    public AuthRequestWrapper(HttpServletRequest request) {
        super(request);
        if (request == null) {
            throw new IllegalArgumentException("empty request");
        }
        this.request = request;

        logger.info("constructor, method: " + request.getMethod());
        logger.info("auth request type: " + request.getHeader("AuthType"));
    }

    @Override
    public String getMethod() {
        String authType = request.getHeader("AuthType");
        if ("POST".equalsIgnoreCase(authType)) {
            return "POST";
        } else if ("PUT".equalsIgnoreCase(authType)) {
            return "PUT";
        } else if ("DELETE".equalsIgnoreCase(authType)) {
            return "DELETE";
        }
        return super.getMethod();
    }
}
