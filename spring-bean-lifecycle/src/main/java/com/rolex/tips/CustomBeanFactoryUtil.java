package com.rolex.tips;

import com.rolex.tips.model.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CustomBeanFactoryUtil {
    public static void refreshBean() {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        User testService = wac.getBean("user", User.class);
        if (null != testService) {
            System.err.println("-----第一次------" + testService);
        }

        //获取BeanFactory  
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) wac.getAutowireCapableBeanFactory();

        //删除bean.  
//        defaultListableBeanFactory.removeBeanDefinition("userService");

        //创建bean信息.  
//        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);

        //动态注册bean.  
//        defaultListableBeanFactory.registerBeanDefinition("userService", beanDefinitionBuilder.getBeanDefinition());



        User testService3 = wac.getBean("user", User.class);
        if (null != testService3) {
            System.err.println("-----第一次------" + testService3.toString());
        }
    }
}