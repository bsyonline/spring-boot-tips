package com.rolex.tips;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordEncoderTest {

    @Test
    public void testPasswordEncoder(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "123";
        String encodePassword = passwordEncoder.encode(password);
        System.out.println("password=" + password);
        System.out.println("encodePassword=" + encodePassword);
        assertEquals(true, passwordEncoder.matches(password, encodePassword));
        assertEquals(false, passwordEncoder.matches("1234", encodePassword));
    }

    @Test
    public void test(){
        String pw = "$2a$10$0HbuHNiBBiY4rtZPbcH.UugzMw2j2VtwxobTKsrKCdl9ywRnFVtrG";
        String version = pw.substring(0,3);
        String level = pw.substring(3,6);
        String salt = pw.substring(6, 29);
        String hash = pw.substring(29, 60);
        System.out.println(pw.length());
        System.out.println(version);
        System.out.println(version.length());
        System.out.println(level);
        System.out.println(level.length());
        System.out.println(salt);
        System.out.println(salt.length());
        System.out.println(hash);
        System.out.println(hash.length());
    }

}
