/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.command.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.rolex.tips.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
public class CollapserTestCommand extends HystrixCollapser<List<User>, User, Long> {

    private Long id;
    RestTemplate restTemplate;

    public CollapserTestCommand(Long id) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("GetUserInfosCollapser"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                        .withMaxRequestsInBatch(100)
                        .withTimerDelayInMilliseconds(20)));
        this.id = id;
    }

    @Override
    public Long getRequestArgument() {
        return id;
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Long>> collapsedRequests) {
        StringBuilder paramsBuilder = new StringBuilder("");
        for (CollapsedRequest<User, Long> request : collapsedRequests) {
            paramsBuilder.append(request.getArgument()).append(",");
        }
        String params = paramsBuilder.toString();
        params = params.substring(0, params.length() - 1);

        System.out.println("createCommand方法执行，params=" + params);
        return new BatchCommand(collapsedRequests);
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Long>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<User, Long> request : collapsedRequests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<User>> {

        public final Collection<CollapsedRequest<User, Long>> requests;

        public BatchCommand(Collection<CollapsedRequest<User, Long>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserInfoService"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetUserInfosCollapserBatchCommand")));
            this.requests = requests;
        }

        @Override
        protected List<User> run() throws Exception {
            // 将一个批次内的商品id给拼接在了一起
            StringBuilder paramsBuilder = new StringBuilder("");
            for (CollapsedRequest<User, Long> request : requests) {
                paramsBuilder.append(request.getArgument()).append(",");
            }
            String params = paramsBuilder.toString();
            params = params.substring(0, params.length() - 1);
            // 将多个商品id合并在一个batch内，直接发送一次网络请求，获取到所有的结果
            String url = "http://localhost:8080/users/list?ids=" + params;
            ResponseEntity<String> exchange = new RestTemplate().exchange(url, HttpMethod.GET, null, String.class);
            List<User> list = new ArrayList<>();
            if (exchange.getStatusCodeValue() == 200) {
                String body = exchange.getBody();
                ObjectMapper mapper = new ObjectMapper();
                list.addAll(mapper.readValue(body, new TypeReference<List<User>>() {
                }));
            }
            return list;
        }

    }
}
