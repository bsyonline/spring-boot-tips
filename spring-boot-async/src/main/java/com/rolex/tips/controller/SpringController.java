package com.rolex.tips.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
@RequestMapping("/spring")
@Slf4j
public class SpringController {

    @GetMapping("/callable")
    public Callable<String> callable() {
        log.info("main thread {}", Thread.currentThread().getName());
        return () -> {
            log.info("async thread {}", Thread.currentThread().getName());
            Thread.sleep(3000);
            return "callable";
        };
    }

    ExecutorService executor = Executors.newFixedThreadPool(5);
    @GetMapping("/deferred")
    public DeferredResult<String> deferredResult() {
        log.info("main thread {}", Thread.currentThread().getName());
        DeferredResult<String> deferredResult = new DeferredResult<String>(5 * 1000L);
        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                log.info("deferred result timeout");
                deferredResult.setErrorResult("timeout");
            }
        });
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                log.info("deferred result completed");
            }
        });

        executor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("async thread {}", Thread.currentThread().getName());
                Thread.sleep(3000);
                deferredResult.setResult("deferred result");
            }
        });
        return deferredResult;
    }

    @GetMapping("/async")
    public WebAsyncTask<String> webAsyncTask() {
        log.info("main thread {}", Thread.currentThread().getName());
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<String>(5 * 1000L, new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("async thread {}", Thread.currentThread().getName());
                Thread.sleep(3000);
                return "webAsyncTask";
            }
        });
        webAsyncTask.onTimeout(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "webAsyncTask timeout";
            }
        });
        webAsyncTask.onCompletion(() -> {
            log.info("webAsyncTask completed");
        });
        return webAsyncTask;
    }
}
