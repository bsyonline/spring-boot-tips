package com.rolex.tips.worker;

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
public class WorkerServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(WorkerServer.class).profiles("worker").run(args);
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
                log.info("worker - {}", LocalDateTime.now());
            }
        }, "worker").start();
        log.info("work server started");
    }
}
