package com.pekall.mdm.auth.testclient.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptorImpl
{
    private MessageDigest m_messageDigest = null;

    private int m_saltLength = 0;

    private SecureRandom m_secureRandom = null;

    public EncryptorImpl(String algorithm, String rngAlgorithm, String saltLength)
            throws NoSuchAlgorithmException
    {
        if ((null == algorithm) || ("".equals(algorithm))) {
            throw new IllegalArgumentException("Error: parameter algorithm must no be null or empty.");
        }
        if ((null == rngAlgorithm) || ("".equals(rngAlgorithm))) {
            throw new IllegalArgumentException("Error: parameter rngAlgorithm must no be null or empty.");
        }
        if ((null == saltLength) || ("".equals(saltLength))) {
            throw new IllegalArgumentException("Error: parameter saltLength must no be null or empty.");
        }
        this.m_messageDigest = MessageDigest.getInstance(algorithm);
        this.m_saltLength = new Integer(saltLength);
        this.m_secureRandom = SecureRandom.getInstance(rngAlgorithm);
        this.m_secureRandom.setSeed(System.currentTimeMillis());
    }

    private byte[] createSalt()
    {
        byte[] salt = new byte[this.m_saltLength];
        this.m_secureRandom.nextBytes(salt);
        return salt;
    }

    private byte[] encrypt(byte[] value, byte[] salt)
    {
        this.m_messageDigest.reset();
        this.m_messageDigest.update(salt);
        this.m_messageDigest.update(value);

        byte[] encryptedValue = this.m_messageDigest.digest();

        byte[] result = new byte[salt.length + encryptedValue.length];
        System.arraycopy(salt, 0, result, 0, salt.length);
        System.arraycopy(encryptedValue, 0, result, salt.length, encryptedValue.length);
        return result;
    }

    public byte[] encrypt(byte[] value)
    {
        if (null == value) {
            throw new IllegalArgumentException("The 'value' parameter must not be null");
        }
        byte[] salt = createSalt();
        return encrypt(value, salt);
    }

    public boolean compare(byte[] inputValue, byte[] encryptedValue)
    {
        if (null == inputValue) {
            throw new IllegalArgumentException("The 'value' parameter must not be null");
        }
        if (null == encryptedValue) {
            throw new IllegalArgumentException("The 'value' parameter must not be null");
        }

        byte[] salt = new byte[this.m_saltLength];
        System.arraycopy(encryptedValue, 0, salt, 0, this.m_saltLength);

        byte[] encryptedInputValue = encrypt(inputValue, salt);
        return MessageDigest.isEqual(encryptedInputValue, encryptedValue);
    }
}
