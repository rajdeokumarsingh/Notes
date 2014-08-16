package com.ray.demo.ssl;

import com.pekall.mdm.common.util.PoliceOfficerInfo;
import com.pekall.mdm.common.util.X509CertParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Controller
@RequestMapping("")
public class HelloController {
    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(value = "/hello" ,method = RequestMethod.GET)
    public String test(ModelMap model) {
        model.addAttribute("message", "just for test");
        return "hello";
    }

    @RequestMapping(value = "/zjipst_cert" ,method = RequestMethod.GET)
    public String getZjipstCert(ModelMap model, HttpServletRequest request) {
        logger.info("get cert: " + request.getHeader(X509CertParser.X_CERT_HEADER));

        PoliceOfficerInfo policeOfficerInfo = X509CertParser.getOfficerInfo(request);

        logger.info("get policeOffice`rInfo: " + policeOfficerInfo.toString());

        model.addAttribute("message", policeOfficerInfo.toString());

        return "hello";
    }

    @RequestMapping(value = "/get_cert" ,method = RequestMethod.GET)
    public String getCertificate(ModelMap model, HttpServletRequest request) {
        logger.info("get cert: " + request.getHeader(X509CertParser.X_CERT_HEADER));

        StringBuilder sb = new StringBuilder("-----BEGIN CERTIFICATE-----\n");
        sb.append(request.getHeader(X509CertParser.X_CERT_HEADER));
        sb.append("\n-----END CERTIFICATE-----");

        try {
            // 实例化证书工厂
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream inputStream = new ByteArrayInputStream(sb.toString().getBytes());

            // 生成证书
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);

            // 关闭证书文件流
            inputStream.close();

            System.out.println("certificate: " + certificate.toString());
            model.addAttribute("message", certificate.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "hello";
    }

    @RequestMapping(value = "/get_cert_attr" ,method = RequestMethod.GET)
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