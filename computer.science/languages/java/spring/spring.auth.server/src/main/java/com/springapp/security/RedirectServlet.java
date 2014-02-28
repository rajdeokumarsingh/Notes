package com.springapp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class RedirectServlet extends org.springframework.web.servlet.FrameworkServlet {

    private static Logger logger = Logger.getLogger("RedirectServlet");

    @Override
    protected void doService(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append("address: " + httpServletRequest.getServerName())
                .append(",port:" + httpServletRequest.getServerPort());
        logger.info(sb.toString());
        // logger.info(httpServletRequest.toString());
        // logger.info(httpServletResponse.toString());
    }
}
