package com.rolex.tips.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
@RequestMapping("/servlet")
@RestController
public class ServletController {

    @Autowired
    HttpServletRequest request;

    @GetMapping("/sync")
    public String sync() throws InterruptedException {
        Thread.sleep(3000);
        return "this is sync invoke";
    }

    @GetMapping("/async")
    public void async() {
        AsyncContext asyncContext = request.startAsync();
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                log.info("onComplete");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                log.info("onTimeout");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                log.info("onError");
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                log.info("onStartAsync");
            }
        });
        asyncContext.setTimeout(50000);
        asyncContext.start(() -> {
            try {
                Thread.sleep(3000);
                log.info("async thread " + Thread.currentThread().getName());
                asyncContext.getResponse().getWriter().println("this is async invoke");
                asyncContext.complete();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }

}
