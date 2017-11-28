package com.deltacom.app.utils;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class PasswordEncrypterTest {
    @Test
    public void encryptPassword() throws Exception {
        final int PASSWORD_STRENGTH = 11;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(PASSWORD_STRENGTH);
        String encryptedPassword = PasswordEncrypter.encryptPassword("123456");

        assertTrue(encoder.matches("123456", encryptedPassword));
    }

    @Test(expected = InvocationTargetException.class)
    public void constructorTest() throws Exception  {
        Constructor<PasswordEncrypter> constructor = PasswordEncrypter.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        constructor.newInstance();
    }

}