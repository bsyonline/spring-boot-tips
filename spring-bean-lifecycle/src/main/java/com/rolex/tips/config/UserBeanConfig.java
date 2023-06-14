package com.rolex.tips.config;

import com.rolex.tips.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Configuration
public class UserBeanConfig {

    @Bean
    public User user(){
        User user = new User();
        user.setName("jack");
        return user;
    }
}
