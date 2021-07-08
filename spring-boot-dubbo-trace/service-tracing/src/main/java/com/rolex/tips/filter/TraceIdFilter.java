package com.rolex.tips.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.UUID;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
@Activate(group = {PROVIDER, CONSUMER})
public class TraceIdFilter implements Filter {

    public static final String TRACE_ID = "TraceId";
    public static final String SPAN_ID = "SpanId";
    public static final String PARENT_SPAN_ID = "ParentSpanId";
    public static final String SEQ = "Seq";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getContext();

        // before
        if (rpcContext.isProviderSide()) {
            System.out.println("consumer side: traceId=" + rpcContext.getAttachment(TRACE_ID) + ", parentSpanId=" + rpcContext.getAttachment(PARENT_SPAN_ID) + ", seq=" + rpcContext.getAttachment(SEQ));
            // get traceId from dubbo consumer，and set traceId to MDC
            String traceId = rpcContext.getAttachment(TRACE_ID);
            String parentSpanId = rpcContext.getAttachment(PARENT_SPAN_ID);
            String seq = rpcContext.getAttachment(SEQ);
            String spanId;
            if (null == parentSpanId) {
                spanId = "0";
            } else {
                spanId = parentSpanId + "." + seq;
            }
            MDC.put(TRACE_ID, traceId);
            MDC.put(SPAN_ID, spanId);
            System.out.println("provider side: traceId=" + traceId + ", parentSpanId=" + parentSpanId + ", spanId=" + spanId + ", seq=" + seq);
        }

        if (rpcContext.isConsumerSide()) {
            System.out.println("consumer side: traceId=" + rpcContext.getAttachment(TRACE_ID) + ", parentSpanId=" + rpcContext.getAttachment(PARENT_SPAN_ID) + ", seq=" + rpcContext.getAttachment(SEQ));
            // get traceId from MDC, and set traceId to rpcContext
            String traceId = StringUtils.isEmpty(MDC.get(TRACE_ID)) && StringUtils.isEmpty(rpcContext.getAttachment(TRACE_ID)) ?
                    UUID.randomUUID().toString() :
                    StringUtils.isEmpty(rpcContext.getAttachment(TRACE_ID))? MDC.get(TRACE_ID) : rpcContext.getAttachment(TRACE_ID);
            String parentSpanId = rpcContext.getAttachment(PARENT_SPAN_ID);
            String traceSeq = StringUtils.isEmpty(rpcContext.getAttachment(SEQ)) ? "0" : rpcContext.getAttachment(SEQ);
            String localSeq = MDC.get(SEQ);
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


            rpcContext.setAttachment(TRACE_ID, MDC.get(TRACE_ID));
            rpcContext.setAttachment(PARENT_SPAN_ID, MDC.get(SPAN_ID));
            String seq = String.valueOf(Integer.parseInt(MDC.get(MDC.get(TRACE_ID) + "_" + MDC.get(PARENT_SPAN_ID))) + 1);
            rpcContext.setAttachment(SEQ, seq);
            MDC.put(MDC.get(TRACE_ID) + "_" + MDC.get(PARENT_SPAN_ID), seq);
            System.out.println("consumer side: traceId=" + traceId + ", parentSpanId=" + parentSpanId + ", spanId=" + spanId + ", seq=" + seq);
        }

        Result result = invoker.invoke(invocation);

        // after
        if (rpcContext.isProviderSide()) {
            // clear traceId from MDC
            MDC.remove(MDC.get(TRACE_ID) + "_" + MDC.get(PARENT_SPAN_ID));
            MDC.remove(PARENT_SPAN_ID);
            MDC.remove(SPAN_ID);
            MDC.remove(TRACE_ID);
        }

        return result;
    }

}
