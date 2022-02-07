package com.rolex.redis.hearbeat;

import com.alibaba.fastjson.JSONObject;
import com.rolex.redis.model.RegistryInfo;
import com.rolex.redis.pub.PublisherService;
import com.rolex.redis.util.NetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
@Slf4j
public class HeartbeatService {

    @Value("${node.id}")
    int nodeId;
    @Value("${server.port}")
    int port;
    @Resource
    private PublisherService publisherService;

    public void heartbeat() throws SocketException, UnknownHostException {
        String heartbeat = JSONObject.toJSONString(new RegistryInfo(nodeId, NetUtils.getSiteIP(), port, System.currentTimeMillis()));
        log.info("上报心跳：{}", heartbeat);
        publisherService.pushMsg(heartbeat);
    }
}
