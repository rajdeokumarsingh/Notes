package com.springapp.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    public void destroy() {
        logger.info("destroy");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        logger.info("doFilter: " + req.toString());

        AuthRequestWrapper wrapper = new AuthRequestWrapper((HttpServletRequest) req);
        chain.doFilter(wrapper, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        logger.info("init");
    }

}
