package com.ray.demo.ssl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.cert.X509Certificate;

@Controller
@RequestMapping("/")
public class HelloController {
    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(value = "/hello" ,method = RequestMethod.GET)
	public String printWelcome(ModelMap model, HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        StringBuilder stringBuilder = new StringBuilder();
        if (certs != null) {
            for (X509Certificate cert : certs) {
                logger.info("certs: " + cert.toString());
                stringBuilder.append(cert.toString());
            }
        } else {
            logger.info("no certificate");
        }

        model.addAttribute("message", stringBuilder.toString());
		return "hello";
	}
}