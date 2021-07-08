package com.rolex.tips.controller;

import com.rolex.tips.api.CompletableFutureService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class CompletableFutureController {
    @DubboReference(version = "1.0.0", timeout = 20000)
    private CompletableFutureService demoService;

    @RequestMapping("/async/completableFuture")
    public String register2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        CompletableFuture<String> fooCompletableFuture = demoService.fooCompletableFuture();
        CompletableFuture<String> barCompletableFuture = demoService.barCompletableFuture();
        fooCompletableFuture.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                if (throwable != null) {
                    throwable.printStackTrace();
                } else {
                    System.out.println("fooResult=" + s);
                }
            }
        });

        barCompletableFuture.whenComplete((result, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            } else {
                System.out.println("barResult=" + result);
            }
        });

        System.out.println("执行完成，耗时 " + ((System.currentTimeMillis() - start)) + " ms");
        return new Date().toString();
    }
}
