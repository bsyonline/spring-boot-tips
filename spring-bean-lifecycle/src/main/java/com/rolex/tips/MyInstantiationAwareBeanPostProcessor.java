/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2020
 */
@Component("instantiationAwareBeanPostProcessor")
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("com.rolex.alphax.MyInstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("com.rolex.alphax.MyInstantiationAwareBeanPostProcessor.postProcessAfterInstantiation");
        if (bean instanceof Person) {
            Person p = (Person) bean;
            System.out.println("修改之前：name=" + p.name);
            p.name = "Ann";
            System.out.println("修改之后：name=" + p.name);
        }
        return true;
    }

    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.println("com.rolex.alphax.MyInstantiationAwareBeanPostProcessor.postProcessProperties");
        if (bean instanceof Person) {
            Person p = (Person) bean;
            System.out.println("修改之前：name=" + p.name);
            p.name = "Bob";
            System.out.println("修改之后：name=" + p.name);
        }
        return null;
    }
}
