package com.rolex.tips;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        //1
//        SpringApplication.run(App.class, args);
        //2
//        SpringApplication springApplication = new SpringApplication(App.class);
//        springApplication.run(args);
        //3
        new SpringApplicationBuilder(App.class).run(args);
    }
}
