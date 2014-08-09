/**
 * 2009-5-20
 */
package org.zlex.chapter10_2;

import static org.junit.Assert.*;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

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
        // 实例化证书工厂
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

        // 取得证书文件流
//        FileInputStream in = new FileInputStream(
//                "/home/jiangrui/git/note/computer.science/security/certificate/openssl/certs/ca.cer");

        ByteArrayInputStream inputStream = new ByteArrayInputStream(CERT_WITH_CHINESE_CHAR.getBytes());

        // 生成证书
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);


        // 关闭证书文件流
        inputStream.close();


//        for (String ext : certificate.getExtendedKeyUsage()) {
//            System.out.println("certificate: " + ext);
//        }
        System.out.println("getIssuerDN: " + certificate.getIssuerDN());
        System.out.println("getIssuerX500Principal: " + certificate.getIssuerX500Principal().toString());
        System.out.println("certificate: " + certificate.getCriticalExtensionOIDs());

        System.out.println("certificate type: " + certificate.getType());

        System.out.println("certificate getKeyUsage: " + certificate.getKeyUsage());
        System.out.println("certificate: " + certificate.toString());



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
}
