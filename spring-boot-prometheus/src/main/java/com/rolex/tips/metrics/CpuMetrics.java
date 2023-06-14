package com.rolex.tips.metrics;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class CpuMetrics {
    static final Counter cupCounter = Counter.build().name("cpu_metrics").register();
    static final Gauge cpuGauge = Gauge.build().name("cpu_gauge").labelNames("").register();
}
