package com.rolex.tips.controller;

import com.rolex.tips.utils.MessageUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class I18nController {
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return MessageUtils.get("10001", name) +". "+ MessageUtils.get("10002", "Java", "Python");
    }
}
