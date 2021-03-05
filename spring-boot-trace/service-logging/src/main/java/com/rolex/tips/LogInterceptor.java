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
    public static final String PARENT_SPAN_ID = "ParentSpanId";
    public static final String SEQ = "Seq";
    private Environment environment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String appName = environment.getProperty("spring.application.name");
        String traceId = StringUtils.isEmpty(request.getHeader(TRACE_ID)) ? UUID.randomUUID().toString() : request.getHeader(TRACE_ID);
        String parentSpanId = request.getHeader(PARENT_SPAN_ID);
        String traceSeq = StringUtils.isEmpty(request.getHeader(SEQ)) ? "0" : request.getHeader(SEQ);
        String localSeq = "0";
        String spanId;
        if (null == parentSpanId) {
            spanId = "0";
        } else {
            spanId = parentSpanId + "." + traceSeq;
        }
        MDC.put(TRACE_ID, traceId);
        MDC.put(SPAN_ID, spanId);
        MDC.put(PARENT_SPAN_ID, parentSpanId);
        MDC.put(traceId + "_" + parentSpanId, localSeq);
        MDC.put("appName", appName);
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
        MDC.remove(MDC.get(TRACE_ID) + "_" + MDC.get(PARENT_SPAN_ID));
        MDC.remove(PARENT_SPAN_ID);
        MDC.remove(SPAN_ID);
        MDC.remove(TRACE_ID);
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
