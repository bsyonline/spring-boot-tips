package com.rolex.tips;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@SpringBootApplication
@RestController
public class HelloApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(HelloApp.class).web(WebApplicationType.SERVLET).run(args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
