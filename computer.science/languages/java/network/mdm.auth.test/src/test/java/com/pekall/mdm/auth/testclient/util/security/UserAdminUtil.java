package com.pekall.mdm.auth.testclient.util.security;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class UserAdminUtil {

    private EncryptorImpl m_encryptor = null;

    public UserAdminUtil() {
        this.m_encryptor = createEncryptor("MD5", "SHA1PRNG", "32");
    }

    private EncryptorImpl createEncryptor(String encryptionAlgorithm, String encryptionRandomAlgorithm,
                                          String encryptionRandomAlgorithmSaltLength) {
        EncryptorImpl encryptor = null;
        try {
            encryptor = new EncryptorImpl(encryptionAlgorithm,
                    encryptionRandomAlgorithm, encryptionRandomAlgorithmSaltLength);
        } catch (NoSuchAlgorithmException e) {
        }
        return encryptor;
    }

    public byte[] encrypt(Object value) {
        byte[] valueBytes;
        if ((value instanceof String))
            valueBytes = ((String) value).getBytes();
        else if ((value instanceof byte[]))
            valueBytes = (byte[]) value;
        else {
            throw new IllegalArgumentException("Illegal value type: " + value.getClass().getName());
        }

        if (null != this.m_encryptor) {
            valueBytes = this.m_encryptor.encrypt(valueBytes);
        }
        return valueBytes;
    }

    public boolean compareToEncryptedValue(Object inputValue, byte[] storedValue) {
        byte[] valueBytes;
        if ((inputValue instanceof String))
            valueBytes = ((String) inputValue).getBytes();
        else if ((inputValue instanceof byte[]))
            valueBytes = (byte[]) inputValue;
        else {
            throw new IllegalArgumentException("Illegal value type: " + inputValue.getClass().getName());
        }

        if (null != this.m_encryptor) {
            return this.m_encryptor.compare(valueBytes, storedValue);
        }
        return Arrays.equals(valueBytes, storedValue);
    }

}
