package com.rolex.tips;

import com.rolex.tips.model.User;
import com.rolex.tips.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    User user;
    @Autowired
    UserService userService;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        log.info("user.name={}", user.getName());
        log.info("userService.name={}", userService.getName());
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        //获取BeanFactory
//        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) wac.getAutowireCapableBeanFactory();
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        //删除bean.
        defaultListableBeanFactory.removeBeanDefinition("user");

        //创建bean信息.
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("name","tom");
        //动态注册bean.
        defaultListableBeanFactory.registerBeanDefinition("user", beanDefinitionBuilder.getBeanDefinition());

//        defaultListableBeanFactory.destroySingleton("user");

//        User user = new User();
//        user.setName("tom");

//        defaultListableBeanFactory.registerSingleton("user", user);

        log.info("user.name={}", this.user.getName());
        log.info("userService.name={}", userService.getName());

    }
}
