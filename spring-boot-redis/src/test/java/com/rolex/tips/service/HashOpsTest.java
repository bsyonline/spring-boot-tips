package com.rolex.tips.service;

import com.rolex.tips.model.ExecutorInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class HashOpsTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void test01Add() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        // 存储对象
        String key = "user";
        ops.put(key, "name", "tom");
        ops.put(key, "age", "20");
        ops.put(key, "gender", "male");
        System.out.println(ops.get(key, "name"));
        Set keys = ops.keys(key);
        System.out.println(Arrays.toString(keys.toArray()));
        System.out.println(Arrays.toString(ops.multiGet(key, keys).toArray()));
        ops.delete(key, "gender", "age");
        redisTemplate.delete(key);
    }

    @Test
    public void cart() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        String userId = "1001";
        String goodId = "18";
        String key = "cart:" + userId;
        // 添加商品
        Long incr = ops.increment(key, "18", 1L);
        Long incr1 = ops.increment(key, "19", 1L);
        Long incr2 = ops.increment(key, "20", 1L);
        System.out.println(incr);
        System.out.println(incr1);
        System.out.println(incr2);
        // 增加数量
        Long count1 = ops.increment(key, "18", 1L);
        System.out.println(count1);
        Long count = Long.parseLong((String) ops.get(key, "18"));
        System.out.println("id 18 商品数量：" + count);
        // 商品总数
        Long len = ops.size(key);
        System.out.println(len);
        // 删除商品
        // 所有商品总数
        Map<Object, Object> map = ops.entries(key);
        System.out.println(map);
        redisTemplate.delete(key);
    }

    @Test
    public void test() {
        String host1 = "zujian1:9030";
        String host2 = "zujian2:9030";
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        String key = "dispatcher";
        ops.put(key, host1, String.valueOf(System.currentTimeMillis()));
        ops.put(key, host2, String.valueOf(System.currentTimeMillis()));

        Map<Object, Object> dispatcherMap = ops.entries("dispatcher");
        Set<String> dispatchers = dispatcherMap.entrySet()
                .stream()
                .filter(kv -> System.currentTimeMillis() - (Long.valueOf((String) kv.getValue())) < 40 * 1000)
                .map(kv -> (String) kv.getKey())
                .collect(Collectors.toSet());
        System.out.println(dispatchers);
    }

    @Test
    public void test2() {
        String host1 = "zujian1:9030";
        String host2 = "zujian2:9030";
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        String key = "executor";
        ops.put(key, "1:host", "zujian1");
        ops.put(key, "1:port", "9020");
        ops.put(key, "1:type", "system");
        ops.put(key, "1:tenantCode", "");
        ops.put(key, "1:ts", String.valueOf(System.currentTimeMillis()));

        ops.put(key, "2:host", "zujian2");
        ops.put(key, "2:port", "9020");
        ops.put(key, "2:type", "tenant");
        ops.put(key, "2:tenantCode", "zhangsan");
        ops.put(key, "2:ts", String.valueOf(System.currentTimeMillis()));

        ops.put(key, "3:host", "zujian3");
        ops.put(key, "3:port", "9020");
        ops.put(key, "3:type", "tenant");
        ops.put(key, "3:tenantCode", "lisi");
        ops.put(key, "3:ts", String.valueOf(System.currentTimeMillis()));

        Map<Object, Object> dispatcherMap = ops.entries("executor");

        Map<Long, Map<String, String>> map = new HashMap<>();

        for (Map.Entry<Object, Object> kv : dispatcherMap.entrySet()) {
            String key1 = (String) kv.getKey();
            String value = (String) kv.getValue();
            String[] split = key1.split(":", -1);
            Long id = Long.valueOf(split[0]);
            String k = split[1];
            if (map.get(id) == null) {
                Map m = new HashMap();
                m.put(k, value);
                map.put(id, m);
            } else {
                map.get(id).put(k, value);
            }
        }

        System.out.println(map);


        List<ExecutorInfo> allExecutors = new ArrayList<>();
        for (Map.Entry<Long, Map<String, String>> kv : map.entrySet()) {
            Long ts = Long.valueOf(kv.getValue().get("ts"));
            if (System.currentTimeMillis() - ts < 40 * 1000) {
                ExecutorInfo executorInfo = new ExecutorInfo();
                executorInfo.setId(kv.getKey());
                executorInfo.setHost(kv.getValue().get("host"));
                executorInfo.setUrl(kv.getValue().get("url"));
                executorInfo.setTs(ts);
                executorInfo.setType(kv.getValue().get("type"));
                executorInfo.setTenantCode(kv.getValue().get("tenantCode"));
                allExecutors.add(executorInfo);
            }
        }

        System.out.println("allExecutors=" + allExecutors);

        Map<String, List<ExecutorInfo>> collect = allExecutors.stream().collect(Collectors.groupingBy(kv -> kv.getType(), Collectors.toList()));

        System.out.println("group allExecutors=" + collect);


        int groupId = 1;
        String tenantCode = "zhangsan";
        Set<Long> exclude = new HashSet<>();
        for (Map.Entry<Long, Map<String, String>> kv : map.entrySet()) {
            Long id = kv.getKey();
            Map<String, String> value = kv.getValue();
            Long ts = Long.valueOf(value.get("ts"));
            String tenantCode1 = value.get("tenantCode");
            String type1 = value.get("type");
            String type;
            if (Objects.equals(0, groupId)) {
                type = "system";
            } else {
                type = "tenant";
            }
            if (System.currentTimeMillis() - ts > 40 * 1000) {
                exclude.add(id);
                continue;
            }
            if (!Objects.equals(tenantCode1, tenantCode) && Objects.equals("tenant", type1)) {
                exclude.add(id);
                continue;
            }
            if (!Objects.equals(type, type1)) {
                exclude.add(id);
                continue;
            }
        }
        for (Long id : exclude) {
            map.remove(id);
        }
        System.out.println("all available executors:" + map);


    }

}