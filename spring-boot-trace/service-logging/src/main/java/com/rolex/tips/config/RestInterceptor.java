package com.rolex.tips.config;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.rolex.tips.LogInterceptor.SPAN_ID;
import static com.rolex.tips.LogInterceptor.TRACE_ID;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Component
public class RestInterceptor implements ClientHttpRequestInterceptor {

    @Value("${spring.application.name}")
    String name;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().set(TRACE_ID, MDC.get(TRACE_ID));
        httpRequest.getHeaders().set(SPAN_ID, MDC.get(SPAN_ID));
        return clientHttpRequestExecution.execute(httpRequest, body);
    }
}
