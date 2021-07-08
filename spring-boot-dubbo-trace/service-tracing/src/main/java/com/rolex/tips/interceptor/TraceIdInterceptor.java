package com.rolex.tips.interceptor;

import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static com.rolex.tips.filter.TraceIdFilter.SEQ;
import static com.rolex.tips.filter.TraceIdFilter.TRACE_ID;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Component
public class TraceIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RpcContext context = RpcContext.getContext();
        String traceId = context.getAttachment(TRACE_ID) == null ? UUID.randomUUID().toString() : context.getAttachment(TRACE_ID);
        MDC.put(TRACE_ID, traceId);
        String localSeq = "0";
        MDC.put(SEQ, localSeq);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(TRACE_ID);
    }
}
