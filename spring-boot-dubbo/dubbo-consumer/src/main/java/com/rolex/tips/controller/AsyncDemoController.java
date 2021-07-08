package com.rolex.tips.controller;

import com.rolex.tips.api.AsyncDemoService;
import com.rolex.tips.api.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class AsyncDemoController {
    @DubboReference(version = "1.0.0", timeout = 20000)
    private AsyncDemoService demoService;

    @RequestMapping("/async/register")
    public String register() {
        long start = System.currentTimeMillis();
        demoService.foo();
        demoService.bar();
        System.out.println("执行完成，耗时 " + ((System.currentTimeMillis() - start)) + " ms");
        return new Date().toString();
    }

    @RequestMapping("/async/future")
    public String register1() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        String fooResult1 = demoService.foo();
        System.out.println("fooResult1=" + fooResult1);
        Future<String> fooFuture = RpcContext.getContext().getFuture();

        String barResult1 = demoService.bar();
        System.out.println("barResult1=" + barResult1);
        Future<String> barFuture = RpcContext.getContext().getFuture();

        String fooResult2 = fooFuture.get();
        System.out.println("fooResult2=" + fooResult2);

        String barResult2 = barFuture.get();
        System.out.println("barResult2=" + barResult2);
        System.out.println("执行完成，耗时 " + ((System.currentTimeMillis() - start)) + " ms");
        return new Date().toString();
    }

}
