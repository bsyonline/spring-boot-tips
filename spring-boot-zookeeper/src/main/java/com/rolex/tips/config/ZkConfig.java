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
@ConfigurationProperties(prefix = "zk")
@Data
public class ZkConfig {

    /**
     * 连接地址
     */
    private String url;

    /**
     * 超时时间(毫秒)，默认1000
     */
    private int timeout = 1000;

    /**
     * 重试次数，默认3
     */
    private int retry = 3;

    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(timeout, retry);
        CuratorFramework client = CuratorFrameworkFactory.newClient(url, retryPolicy);
        client.start();
        return client;
    }
}
