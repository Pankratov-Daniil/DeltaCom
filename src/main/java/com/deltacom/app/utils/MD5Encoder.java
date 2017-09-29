package com.deltacom.app.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class for password encryption.
 */
public final class MD5Encoder {
    private MD5Encoder() { }

    /**
     * Encodes password.
     * @param password String representation of password
     * @return MD5 representation of password
     */
    public static String encodePassword(final String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes(), 0, password.length());
        String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
        if (hashedPass.length() < 32) {
            hashedPass = "0" + hashedPass;
        }

        return hashedPass;
    }
}

