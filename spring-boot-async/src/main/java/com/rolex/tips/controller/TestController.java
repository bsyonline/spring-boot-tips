package com.rolex.tips.controller;

import com.rolex.tips.annotation.AsyncTransfer;
import com.rolex.tips.model.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Slf4j
public class TestController {

    @AsyncTransfer(condition = "#parallel>3", asyncMethod = "asyncTest")
    @PostMapping("/test")
    public String test(@RequestBody Job job) {
        log.info("job={}", job);
        return "ok";
    }

}
