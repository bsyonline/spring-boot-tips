/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private int database;
    private String password;
    private int timeout;
    private cluster cluster;
    private String address;

    @Data
    public static class cluster {
        private List<String> nodes;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://".concat(address));
        singleServerConfig.setTimeout(timeout);
        singleServerConfig.setDatabase(database);
        if (password != null && !"".equals(password)) { //有密码
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
    }

//    @Bean
//    public RedissonClient redissonClient(){
//
//        List<String> nodes = cluster.getNodes();
//        if (ObjectUtils.isEmpty(nodes)) {
//            throw new RuntimeException("nodes is not allow empty! please check your redis config");
//        }
//        nodes = cluster.getNodes().stream().map(h -> "redis://".concat(h)).collect(Collectors.toList());
//
//        RedissonClient redisson = null;
//        Config config = new Config();
//        ClusterServersConfig clusterServersConfig = config.useClusterServers()
//                .setScanInterval(2000)
//                .setTimeout(timeout)
//                .addNodeAddress(nodes.toArray(new String[]{}));
//
//        if(StringUtils.isNotBlank(password)) {
//            clusterServersConfig.setPassword(password);
//        }
//        return  Redisson.create(config);
//    }

}
