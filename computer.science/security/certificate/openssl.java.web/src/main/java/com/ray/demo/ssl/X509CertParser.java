package com.ray.demo.ssl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class X509CertParser {

    private static final String X509_CERT_PREFIX = "-----BEGIN CERTIFICATE-----\n";
    private static final String X509_CERT_SUFFIX = "\n-----END CERTIFICATE-----";
    public static final String X_CERT_HEADER = "X-Certificate";
    public static final String CERT_TYPE_X509 = "X.509";
    public static final String x500_DESC_ID = "2.5.4.13";

    private static Logger logger = LoggerFactory.getLogger(X509CertParser.class);

    private static String buildCertificate(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(X509_CERT_PREFIX);
        sb.append(request.getHeader(X_CERT_HEADER));
        sb.append(X509_CERT_SUFFIX);
        return sb.toString();
    }

    public static PoliceOfficerInfo getOfficerInfo(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null!");
        }

        String cert = buildCertificate(request);
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE_X509);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(cert.getBytes());
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
            inputStream.close();

            X500Name x500Name = new JcaX509CertificateHolder(certificate).getSubject();

            RDN cn = x500Name.getRDNs(BCStyle.CN)[0];
            String cnString = IETFUtils.valueToString(cn.getFirst().getValue());
            logger.debug("cn: " + cnString);

            PoliceOfficerCN policeOfficerCN = new PoliceOfficerCN(cnString);

            RDN email = x500Name.getRDNs(BCStyle.EmailAddress)[0];
            String emailString = IETFUtils.valueToString(email.getFirst().getValue());
            logger.debug("email: " + emailString);

            PoliceOfficerDesc policeOfficerDesc = null;
            for (RDN rdn : x500Name.getRDNs()) {
                for (AttributeTypeAndValue attributeTypeAndValue : rdn.getTypesAndValues()) {
                    ASN1ObjectIdentifier identifier = attributeTypeAndValue.getType();
                    ASN1Encodable encodable = attributeTypeAndValue.getValue();

                    if (x500_DESC_ID.equals(identifier.getId())) {
                        logger.debug("description: " + identifier.getId() + ", value: " + encodable.toString());
                        policeOfficerDesc = new PoliceOfficerDesc(encodable.toString());
                        break;
                    }
                }
            }

            if (policeOfficerDesc == null) {
                throw new IllegalArgumentException("can not get description");
            }

            PoliceOfficerInfo policeOfficerInfo = new PoliceOfficerInfo();
            policeOfficerInfo.setEmail(emailString);
            policeOfficerInfo.setName(policeOfficerCN.getName());
            policeOfficerInfo.setIdNum(policeOfficerCN.getIdNum());
            policeOfficerInfo.setImsi(policeOfficerDesc.getImsi());
            policeOfficerInfo.setImei(policeOfficerDesc.getImei());
            policeOfficerInfo.setPoliceNum(policeOfficerDesc.getPoliceNum());

            return policeOfficerInfo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("can not parse certificate: " + cert);
        }
    }
}

