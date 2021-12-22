package com.cbt.agent.bootstrap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tommy on 16/10/16.
 */
public class UtilEncryption {
    public static final Charset UTF_8 = Charset.forName("UTF-8"), ISO8859_1 = Charset.forName("ISO8859-1"),
            GBK = Charset.forName("GBK");

    public static String md5(String... values) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        for (String value : values) {
            if (value != null) messageDigest.update(value.getBytes(UTF_8));
        }
        return toHexString(messageDigest.digest());
    }

    public static String md5(byte[] bytes) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.update(bytes);
        return toHexString(messageDigest.digest());
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (int i = 0, len = bytes.length, value; i < len; i++) {
            value = bytes[i] & 0xff;
            if (value < 0x10) buf.append('0');
            buf.append(Integer.toHexString(value));
        }
        return buf.toString();
    }

    public static String encodeBase64(byte[] bytes) {
        return new sun.misc.BASE64Encoder().encode(bytes);
    }

    public static byte[] decodeBase64(String base64) throws IOException {
        return new sun.misc.BASE64Decoder().decodeBuffer(base64);
    }

}
