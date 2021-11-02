package com.rolex.tips.config;

import lombok.Data;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Configuration
@ConfigurationProperties(prefix = "spring.zookeeper")
@Data
public class ZkConfig {

    /**
     * 连接地址
     */
    private String connectString;

    /**
     * 超时时间(毫秒)，默认1000
     */
    private int baseSleepTimeMs = 1000;

    /**
     * 重试次数，默认3
     */
    private int maxRetries = 3;

    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();
        return client;
    }
}
