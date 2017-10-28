package com.deltacom.app.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Encrypts password with BCrypt algorithm
 */
public class PasswordEncrypter {
    private static final int PASSWORD_STRENGTH = 11;
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(PASSWORD_STRENGTH);

    private PasswordEncrypter() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static String encryptPassword(String password) {
        return encoder.encode(password);
    }
}
