package com.rolex.tips;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor, EnvironmentAware {
    public static final String TRACE_ID = "TraceId";
    public static final String SPAN_ID = "SpanId";
    private Environment environment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(TRACE_ID);
        String spanId = request.getHeader(SPAN_ID);
        traceId = StringUtils.isEmpty(traceId) ? UUID.randomUUID().toString() : traceId;
        spanId = StringUtils.isEmpty(spanId) ? "0" : spanId + "." + (Integer.parseInt(spanId.substring(spanId.length() - 1)) + 1);
        MDC.put(TRACE_ID, traceId);
        MDC.put(SPAN_ID, spanId);
        MDC.put("appName", environment.getProperty("spring.application.name"));

        if (handler instanceof HandlerMethod) {
            Map map = new HashMap();
            HandlerMethod h = (HandlerMethod) handler;
            map.put("Controller", h.getBean().getClass().getName());
            map.put("Method", h.getMethod().getName());
            map.put("Params", getParamString(request.getParameterMap()));
            map.put("URI", request.getRequestURI());
            log.info(new ObjectMapper().writeValueAsString(map));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(TRACE_ID);
        MDC.remove(SPAN_ID);
        return;
    }

    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
