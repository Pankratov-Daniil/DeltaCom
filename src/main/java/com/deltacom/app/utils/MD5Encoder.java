package com.deltacom.app.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class for password encryption.
 */
public final class MD5Encoder {
    private static final int HASH_LENGTH = 32,
                             RADIX = 16,
                             POSITIVE_HASH_NUMBER = 1;

    private MD5Encoder() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

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
        String hashedPass = new BigInteger(POSITIVE_HASH_NUMBER, messageDigest.digest()).toString(RADIX);
        if (hashedPass.length() < HASH_LENGTH) {
            hashedPass = "0" + hashedPass;
        }

        return hashedPass;
    }
}

