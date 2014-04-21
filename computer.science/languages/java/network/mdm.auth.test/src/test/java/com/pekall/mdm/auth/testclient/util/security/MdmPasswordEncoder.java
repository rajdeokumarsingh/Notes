package com.pekall.mdm.auth.testclient.util.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

import org.springframework.security.crypto.password.PasswordEncoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class MdmPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            return null;
        }

        UserAdminUtil userAdminUtil = new UserAdminUtil();

        byte[] encoded = userAdminUtil.encrypt(rawPassword.toString());
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(encoded);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }

        boolean result = false;
        UserAdminUtil userAdminUtil = new UserAdminUtil();
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] decodedPassword = decoder.decodeBuffer(encodedPassword);
            result = userAdminUtil.compareToEncryptedValue(
                    rawPassword.toString(), decodedPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}