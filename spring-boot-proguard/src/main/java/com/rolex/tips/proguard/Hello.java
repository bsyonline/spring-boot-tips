/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.tips.proguard;

import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2018
 */
@Component
public class Hello {
    
    String password;
    
    public String hello(){
        password = "hello world";
        return password.substring(1, 5);
    }
}
