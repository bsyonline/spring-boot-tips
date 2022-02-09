package com.rolex.tips.master;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
@SpringBootApplication
public class MasterServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(MasterServer.class).profiles("master").run(args);
    }

    @PostConstruct
    public void run() throws InterruptedException {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("master - {}", LocalDateTime.now());
            }
        }, "master").start();
        log.info("master server started");
    }
}
