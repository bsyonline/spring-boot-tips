package com.rolex.tips.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class UserController {
    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping("/set/{name}")
    public String set(@PathVariable("name") String name, HttpSession session) {
        session.setAttribute("user", name);
        return serviceName + ": "+ session.getAttribute("user");
    }

    @GetMapping("/get")
    public String get(HttpSession session) {
        return serviceName + ": "+ session.getAttribute("user");
    }
}
