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

    /**
     * Encrypts password
     * @param password plain password
     * @return encrypted password
     */
    public static String encryptPassword(String password) {
        return encoder.encode(password);
    }

    /**
     * Checks if passwords are equals
     * @param rawPassword raw password
     * @param encodedPassword encrypted password
     * @return true if password matches, false otherwise
     */
    public static boolean passwordsEquals(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
