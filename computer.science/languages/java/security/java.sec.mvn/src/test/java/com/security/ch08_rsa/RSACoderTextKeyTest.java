package com.security.ch08_rsa;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * RSA校验
 *
 * @author 梁栋
 * @version 1.0
 */
public class RSACoderTextKeyTest {

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 初始化密钥
     *
     * @throws Exception
     */
    @Before
    public void initKey() throws Exception {

        UUID licenseCode = UUID.randomUUID();
        System.out.println("license code: " + licenseCode.toString());

        // 初始化密钥
        Map<String, String> keyMap = RSACoderTextKey.initKey(licenseCode);

        publicKey = RSACoderTextKey.getPublicKeyString(keyMap);
        privateKey = RSACoderTextKey.getPrivateKeyString(keyMap);

        System.out.println("公钥: \n" + publicKey);
        System.out.println("私钥： \n" + privateKey);
    }

    /**
     * 校验
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        System.out.println("\n---私钥加密——公钥解密---");

        String sourceText = "RSA加密算法";
        System.out.println("原文:\n" + sourceText);

        // 加密
        String encryptText = RSACoderTextKey.encryptByPrivateKey(sourceText, privateKey);
        System.out.println("加密后:\n" + encryptText);

        // 解密
        String restoreText = RSACoderTextKey.decryptByPublicKey(encryptText, publicKey);
        System.out.println("解密后:\n" + restoreText);

        // 校验
        assertEquals(sourceText, restoreText);
    }

    @Test
    public void test2() throws Exception {
        System.out.println("\n---公钥加密——私钥解密---");
        String sourceText = "RSA Encypt Algorithm";
        System.out.println("原文:\n" + sourceText);

        // 加密
        String encryptText = RSACoderTextKey.encryptByPublicKey(sourceText, publicKey);
        System.out.println("加密后:\n" + encryptText);

        // 解密
        String restoreText = RSACoderTextKey.decryptByPrivateKey(encryptText, privateKey);
        System.out.println("解密后: " + restoreText);

        // 校验
        assertEquals(sourceText, restoreText);
    }
}
