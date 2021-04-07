package com.rolex.tips.service;

import com.rolex.tips.api.OrderService;
import com.rolex.tips.api.ProductService;
import org.apache.dubbo.config.annotation.DubboReference;
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
public class OrderServiceImpl implements OrderService {
    @DubboReference(version = "1.0.0")
    private ProductService productService;
    @Override
    public String list() {
        return String.format(" C(%s, %s) \n\t\t-> %s", MDC.get(TRACE_ID), MDC.get(SPAN_ID), productService.list());
    }
}
