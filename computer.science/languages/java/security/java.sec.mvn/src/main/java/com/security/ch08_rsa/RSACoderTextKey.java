package com.security.ch08_rsa;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;


public abstract class RSACoderTextKey {

    /**
     * 非对称加密密钥算法
     */
    public static final String KEY_ALGORITHM = "RSA";


    /**
     * License code sent to user
     */
    private static final String LICENSE_CODE = "LicenseCode";

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA密钥长度
     * 默认1024位，
     * 密钥长度必须是64的倍数，
     * 范围在512至65536位之间。
     */
    private static final int KEY_SIZE = 1024;


    public static final String UTF_8 = "UTF-8";

    /**
     * Decrypt data by private key
     * @param data base64 encoded string
     * @param key base64 encoded string
     * @return decrypted string
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String key)
            throws Exception {
        byte[] result = decryptByPrivateKey(Base64.decodeBase64(data), Base64.decodeBase64(key));
        return new String(result, UTF_8);
    }

    /**
     * 私钥解密
     *
     * @param data
     *            待解密数据
     * @param key
     *            私钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    private static byte[] decryptByPrivateKey(byte[] data, byte[] key)
            throws Exception {

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * Decrypt data with provided public key
     * @param data base64 encoded string
     * @param key base64 encoded string
     * @return decrypted string
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, String key)
            throws Exception {
        byte[] result = decryptByPublicKey(Base64.decodeBase64(data), Base64.decodeBase64(key));
        return new String(result, UTF_8);
    }

    /**
     * 公钥解密
     *
     * @param data
     *            待解密数据
     * @param key
     *            公钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    private static byte[] decryptByPublicKey(byte[] data, byte[] key)
            throws Exception {

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 生成公钥
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());

        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * Encrypt a string by provide public key
     * @param data string to encrypt
     * @param key base64 encoded string
     * @return base64 encoded data
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, String key)
            throws Exception {
        byte[] result = encryptByPublicKey(data.getBytes(UTF_8), Base64.decodeBase64(key));
        return Base64.encodeBase64String(result);
    }

    /**
     * 公钥加密
     *
     * @param data
     *            待加密数据
     * @param key
     *            公钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    private static byte[] encryptByPublicKey(byte[] data, byte[] key)
            throws Exception {

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }


    /**
     * Encrypt a string with provided private key
     * @param data string to decrypt
     * @param key private key encoded with base64
     * @return base64 encoded string
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, String key)
            throws Exception {
        byte[] result = encryptByPrivateKey(data.getBytes(UTF_8), Base64.decodeBase64(key));
        return Base64.encodeBase64String(result);
    }

    /**
     * 私钥加密
     *
     * @param data
     *            待加密数据
     * @param key
     *            私钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    private static byte[] encryptByPrivateKey(byte[] data, byte[] key)
            throws Exception {

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    public static String getPrivateKeyString(Map<String, String> keyMap) {
        return keyMap.get(PRIVATE_KEY);
    }

    public static String getPublicKeyString(Map<String, String> keyMap) {
        return keyMap.get(PUBLIC_KEY);
    }

    /**
     * 初始化密钥
     *
     * @return Map 密钥Map
     * @throws Exception
     */
    public static Map<String, String> initKey() throws Exception {
        UUID licenseCode = UUID.randomUUID();
        return initKey(licenseCode);
    }

    /**
     * 初始化密钥
     *
     * @return Map 密钥Map
     * @throws Exception
     */
    public static Map<String, String> initKey(UUID licenseCode) throws Exception {
        // 实例化密钥对生成器
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);

        // 初始化密钥对生成器
//        keyPairGen.initialize(KEY_SIZE);
        keyPairGen.initialize(KEY_SIZE, new SecureRandom(licenseCode.toString().getBytes(UTF_8)));

        // 生成密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 封装密钥
        Map<String, String> keyMap = new HashMap<String, String>();

        keyMap.put(PUBLIC_KEY, Base64.encodeBase64String(publicKey.getEncoded()));
        keyMap.put(PRIVATE_KEY, Base64.encodeBase64String(privateKey.getEncoded()));
        keyMap.put(LICENSE_CODE, licenseCode.toString());

        return keyMap;
    }
}
