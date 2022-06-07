package com.rolex.tips.tx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@RestController
@SpringBootApplication
@Slf4j
public class TxMsgApp {
    public static void main(String[] args) {
        SpringApplication.run(TxMsgApp.class, args);
    }


}
