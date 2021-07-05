/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.api;

/**
 * @author rolex
 * @since 2020
 */
public class DemoServiceMock implements DemoService {

    @Override
    public String sayHello(String name) {
        return "mock hello";
    }

    @Override
    public void register(String name) {
        System.out.println("register mock");
    }


}
