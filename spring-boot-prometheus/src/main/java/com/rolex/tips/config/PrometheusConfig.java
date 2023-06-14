package com.rolex.tips.config;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.exporter.PushGateway;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRegistration;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Configuration
public class PrometheusConfig {

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        DefaultExports.initialize();
        return new ServletRegistrationBean(new MetricsServlet(), "/metrics");
    }

    @Bean
    public PushGateway getPushGateway() {
        return new PushGateway("localhost:9091");
    }

    @Bean
    public Counter getCounter() {
        return Counter.build()
                .name("cpu_counter")
                .labelNames("cpu_counter")
                .help("cpu_counter")
                .register();
    }

    @Bean
    public Gauge getGauge() {
        return Gauge.build()
                .name("cpu_gauge")
                .help("cpu_gauge")
                .register();
    }
}
