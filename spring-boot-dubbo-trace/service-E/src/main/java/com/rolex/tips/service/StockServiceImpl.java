package com.rolex.tips.service;

import com.rolex.tips.api.StockService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.MDC;

import static com.rolex.tips.filter.TraceIdFilter.SPAN_ID;
import static com.rolex.tips.filter.TraceIdFilter.TRACE_ID;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@DubboService(version = "1.0.0")
public class StockServiceImpl implements StockService {
    @Override
    public String list() {
        return String.format("E(%s, %s)", MDC.get(TRACE_ID), MDC.get(SPAN_ID));
    }
}
