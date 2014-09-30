/**
 * 2009-5-20
 */
package org.zlex.chapter10_2;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static org.junit.Assert.*;

/**
 * 证书校验
 * 
 * @author 梁栋
 * @version 1.0
 */
public class CertificateCoderTest {

	private String password = "pekall1234";

	private String alias = "1";

	private String certificatePath =
            "/Users/jiangrui/git/note/computer.science/security/certificate/openssl/certs/ca.cer";

	private String keyStorePath =
            "/Users/jiangrui/git/note/computer.science/security/certificate/openssl/certs/ca.p12";

	/**
	 * 公钥加密——私钥解密
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() {

		try {
			System.err.println("公钥加密——私钥解密");
			String inputStr = "Ceritifcate";
			byte[] data = inputStr.getBytes();

			// 公钥加密
			byte[] encrypt = CertificateCoder.encryptByPublicKey(data,
					certificatePath);

			// 私钥解密
			byte[] decrypt = CertificateCoder.decryptByPrivateKey(encrypt,
					keyStorePath, alias, password);

			String outputStr = new String(decrypt);

			System.err.println("加密前:\n" + inputStr);

			System.err.println("解密后:\n" + outputStr);

			// 验证数据一致
			assertArrayEquals(data, decrypt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	/**
	 * 私钥加密——公钥解密
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2() {

		System.err.println("私钥加密——公钥解密");

		String inputStr = "sign";
		byte[] data = inputStr.getBytes();

		try {
			// 私钥加密
			byte[] encodedData = CertificateCoder.encryptByPrivateKey(data,
					keyStorePath, alias, password);

			// 公钥加密
			byte[] decodedData = CertificateCoder.decryptByPublicKey(
					encodedData, certificatePath);

			String outputStr = new String(decodedData);

			System.err.println("加密前:\n" + inputStr);
			System.err.println("解密后:\n" + outputStr);

			// 校验
			assertEquals(inputStr, outputStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * 签名验证
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSign() {

		try {
			String inputStr = "签名";
			byte[] data = inputStr.getBytes();
			System.err.println("私钥签名——公钥验证");

			// 产生签名
			byte[] sign = CertificateCoder.sign(data, keyStorePath, alias,
					password,certificatePath);
			System.err.println("签名:\n" + Hex.encodeHexString(sign));

			// 验证签名
			boolean status = CertificateCoder.verify(data, sign,
					certificatePath);
			System.err.println("状态:\n" + status);

			// 校验
			assertTrue(status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

    @Test
    public void testCert() throws Exception {

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");


        ByteArrayInputStream inputStream = new ByteArrayInputStream(CERT_WITH_CHINESE_CHAR_1.getBytes());

        // 生成证书
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);


        // 关闭证书文件流
        inputStream.close();

        X500Name x500Name = new JcaX509CertificateHolder(certificate).getSubject();

        RDN cn = x500Name.getRDNs(BCStyle.CN)[0];
        String cnString = IETFUtils.valueToString(cn.getFirst().getValue());
        System.out.println("cn: " + cnString);

        RDN email = x500Name.getRDNs(BCStyle.EmailAddress)[0];
        String emailString = IETFUtils.valueToString(email.getFirst().getValue());
        System.out.println("email: " + emailString);


        RDN[] rdns = x500Name.getRDNs();
        for (RDN rdn : x500Name.getRDNs()) {
            for (AttributeTypeAndValue attributeTypeAndValue : rdn.getTypesAndValues()) {
                ASN1ObjectIdentifier identifier = attributeTypeAndValue.getType();
                ASN1Encodable encodable = attributeTypeAndValue.getValue();

                if ("2.5.4.13".equals(identifier.getId())) {
                    System.out.println("rdn, type:" + identifier.getId() + ", value: " + encodable.toString());
                }
            }
        }
    }

    public static final String CERT_NO_NEW_LINE = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDHTCCAoYCCQC7ge3sG6uBsTANBgkqhkiG9w0BAQUFADCBlzELMAkGA1UEBhMCQ04xEDAOBg" +
            "NVBAgTB0JlaWppbmcxEDAOBgNVBAcTB0JlaWppbmcxDzANBgNVBAoTBlBla2FsbDETMBEGA1UECxM" +
            "KUGVrYWxsIExURDEbMBkGA1UEAxMSUGVrYWxsIFJvb3Qgc3NsIENBMSEwHwYJKoZIhvcNAQkBFhJzd" +
            "XBwb3J0QHBla2FsbC5jb20wHhcNMTQwODA4MDczNzA4WhcNMTUwODA4MDczNzA4WjCBiTELMAkGA1U" +
            "EBhMCQ04xCzAJBgNVBAgMAkJKMQswCQYDVQQHDAJCSjEUMBIGA1UECgwLUGVrYWxsIEx0ZC4xFDASBg" +
            "NVBAsMC0RldmVsb3BtZW50MRQwEgYDVQQDDAtDbGllbnQgVGVzdDEeMBwGCSqGSIb3DQEJARYPdGVzd" +
            "EBwZWthbGwuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2pfBSxyNI/Hq4Djvxzm" +
            "2zax7JkWeeLMKgkzhXtBWB/6v+nDmTKVa5FY6GcHrpiOvktKWBPHOBB09zUmLz51inJ3uu+b/GBU1QJ" +
            "gHXnqrX0u9V7NDVC4bVlZShxKzwpAvg0G1BRVWocQfXWJFpIh8PBcLcXOnC47Fsm6PVX7I76GjCT0FoO" +
            "8wgywm30RNCTKpQXatqtx43Fmp9w0Sk4Sp44OMB2ByfbIJBeQNjf61jDlH/n9JwBErcb1k15PUqf6FO9" +
            "zhwZMVOUi0h5Kt//oG07wG3E3byts2Hp64Pkewmm7wUnwyebb1oCnJ+ecISzD4uwgqnh+x61kYMDW2hdQ" +
            "TcQIDAQABMA0GCSqGSIb3DQEBBQUAA4GBAH5XBDl6IJvvDHwgBX6PJhFwNu5WIleRwJkk7yd49blyBOnF" +
            "4srxVrLtncbt5LKQQG0vqry0eZ95N8nfrcPWw0K8yWxfj/NqKmH+OU2sc8t/m5tF6kw0xnh46u/puNqaOK" +
            "3owm6pN/js7L2deEr9XjxkRh2/1Ft9wkxfpAr/atPL" +
            "\n-----END CERTIFICATE-----";

    public static final String CERT_WITH_CHINESE_CHAR = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDnDCCAoSgAwIBAgIINVz/2WEYk7EwDQYJKoZIhvcNAQEFBQAwbzELMAkGA1UEBhMCQ04xEjAQBgNVB" +
            "AgMCea1meaxn+ecgTESMBAGA1UEBwwJ5p2t5bee5biCMQ8wDQYDVQQKDAZaSklQU1QxDzANBgNVBAsMBl" +
            "pKSVBTVDEWMBQGA1UEAwwNWkpJUFNUX1JPT1RDQTAeFw0xNDA3MDIwNjI0NDZaFw00NDA2MjQwNjI0NDZ" +
            "aMG8xCzAJBgNVBAYTAkNOMRIwEAYDVQQIDAnmtZnmsZ/nnIExEjAQBgNVBAcMCeadreW3nuW4gjEPMA0GA" +
            "1UECgwGWkpJUFNUMQ8wDQYDVQQLDAZaSklQU1QxFjAUBgNVBAMMDVpKSVBTVF9ST09UQ0EwggEiMA0GCSq" +
            "GSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDIEK53jzkkjvOfgVeVtPm/ydrn5tH+juVKvZmTSqPyusOOAsWFZ" +
            "1SPxX2C/BKb8/ZoDBqaEhhVLXFfcVS2wFfFzgW2e7yjl60RwSXTe3sA3/WQbjrQTeGDPInt57gJ2RzfNCYQ" +
            "BaFaNaEpUhVCmuAB3UMdxgVg1HWd8ijmlOyDBWY+Tbu/5AUvgWT2N+GkNO6/gYVtFXifLPEkwLjlP1n8bzqX" +
            "+mBW2O1zs9lnsyUSmhP7gbIDT9V4VOCP5eAFRfWw6n201s0LRS+UQsY2+0/6AaE4EjZhE0CNBG4zhaiPAauB" +
            "16dP4uzTAjd7oYT/bI2RH/+MDg1q7A0wd4eieG+bAgMBAAGjPDA6MAsGA1UdDwQEAwIAhjAMBgNVHRMEBTA" +
            "DAQH/MB0GA1UdDgQWBBSwsZLL7P6r5V20McPakp0Os0ar+zANBgkqhkiG9w0BAQUFAAOCAQEAOZdvhPRO3kj" +
            "fSnJevRnxvgafMnhLSKaoJkACVruH8ply8Br2r0VfivkIx+wYSn3FCfO7FKAym9FkziyRgrHO+Ehefgdo2zkF" +
            "Gaf1HnttkOTStX0vhe/e3wOcZxALLQ79IaKisD+dCTiumOQ0zyOZo4vM+vsogSkeFpsBDkVJaiBin5EKa/Ck/9" +
            "enDJ3U4Oz8GsZ12Z+NnaZP6qZfmNvwk74CyQ8SDQr5KwHiZpidHy43JHytIpC48AQTL+OB0zX3ritSPb/vMfzFoo" +
            "lxal7Lqt5npQcrGRwhIL5CLh2aQm8TW+xjGrqY5WqMAfeo7/m9r5L1IRa+g46zTndYLUGuDA==" +
            "\n-----END CERTIFICATE-----";

    public static final String CERT_WITH_CHINESE_CHAR_1 = "-----BEGIN CERTIFICATE-----\n" +
            "MIIEHzCCAwegAwIBAgIINV0A6AAAAGswDQYJKoZIhvcNAQEFBQAwbjELMAkGA1UE" +
            "BhMCQ04xEjAQBgNVBAgMCea1meaxn+ecgTESMBAGA1UEBwwJ5p2t5bee5biCMQ8w" +
            "DQYDVQQKDAZaSklQU1QxDzANBgNVBAsMBlpKSVBTVDEVMBMGA1UEAwwMWkpJUFNU" +
            "X1NVQkNBMB4XDTE0MDgxMzA4MDMwN1oXDTE3MDgxMjA4MDMwN1owgccxCzAJBgNV" +
            "BAYTAkNOMRMwEQYDVQQIDAozMTY1NDg3NDQ1MRMwEQYDVQQHDAoxMjM0NTY3ODkw" +
            "MRMwEQYDVQQKDAo5ODc2NTQzMjEwMSAwHgYJKoZIhvcNAQkBFhFUZXN0VXNlckBU" +
            "ZXN0LmNvbTEwMC4GA1UEDQwnMTIzNDU2Nzg5MTIzNDU2IGh1YXNoYW4wOTExODg4" +
            "ODggeGpoMTIzMSUwIwYDVQQDDBzkuK3mloflkI0gMTIzNDU2Nzg5MDEyMzQ1Njc4" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgQ37kwjg0kBuMRO3qHPU" +
            "aP0FPU9LWgF5JTpHKhV40V+65+fVF4jzUyoiscLxmH7+uSB6XzegDvZwb2pmVACf" +
            "4Cdsg/s/PIlonhBkxsPhp6FEwYIOX02D9I11XGKz7jQb2DasIUW5Ph4SEjX/hi86" +
            "mAdJL2thCkcschupBlztF+ZReGsUhV4zguTiAoKEqdh+Z1yFFOWTFOA5yhyt9Y1U" +
            "C2Iod2kOdA4f3VTy5MR1EsFlvLVaRLUR3nXhovXCRw/eG4xPnFD1nzgsXn9QE1jt" +
            "tqaNbr7VGloJPlrgwJxJReGlPE7yuNDpKRzxU/aOmCZsYy7NYyfuMK4QPlBfdbq2" +
            "ywIDAQABo2cwZTATBgNVHSUEDDAKBggrBgEFBQcDBDAOBgNVHQ8BAf8EBAMCADAw" +
            "HwYDVR0jBBgwFoAUXiD9Qp8PdBmsSkFBaqLiFsn7CbkwHQYDVR0OBBYEFNryCYrm" +
            "ILwEj1jYJ6Wdr54EvDl+MA0GCSqGSIb3DQEBBQUAA4IBAQBWkZc1jfS27RcafRPY" +
            "NN8UFzQu99d/rUswX4+bXAWr2/xhjUvEOLeWYBoyclqXo1wk65IAHqK4V0d3ITTd" +
            "cDkpTPEUvI6hq4cBCbW9VtoiMvgEto8f3z9nmOcxnSSjTOvCVpUcA8r1BsIwoWkL" +
            "lIzCSADkxtVfMeY/Pq2iV7DZqCCtHxe1gC4y6my17/UAUsM9jQNJ7/rHToL0c7A0" +
            "qtXlin2VN4DKW1ue6rybwDPl1rV2sShcN47e718pN3snVOlSxMoyDt22Y4PGTWv6" +
            "rpD78JuLtM8RMD3ZT3F2PsdbcuwgrW5YWLt3hofCntXHJDyrBkzdAhwziIfswtBT" +
            "5m/c" +
            "\n-----END CERTIFICATE-----";
}
