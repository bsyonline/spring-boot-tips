package com.rolex.tips.config;

import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.rolex.tips.LogInterceptor.PARENT_SPAN_ID;
import static com.rolex.tips.LogInterceptor.SEQ;
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
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().set(TRACE_ID, MDC.get(TRACE_ID));
        httpRequest.getHeaders().set(PARENT_SPAN_ID, MDC.get(SPAN_ID));
        String seq = String.valueOf(Integer.parseInt(MDC.get(MDC.get(TRACE_ID) + "_" + MDC.get(PARENT_SPAN_ID))) + 1);
        httpRequest.getHeaders().set(SEQ, seq);
        MDC.put(MDC.get(TRACE_ID) + "_" + MDC.get(PARENT_SPAN_ID), seq);
        return clientHttpRequestExecution.execute(httpRequest, body);
    }
}
