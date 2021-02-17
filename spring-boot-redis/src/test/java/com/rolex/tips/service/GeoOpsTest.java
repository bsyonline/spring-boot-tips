package com.rolex.tips.service;

import com.rolex.tips.model.PointInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GeoOpsTest {


    private List<PointInfo> points;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @BeforeEach
    public void init() {
        points = new ArrayList<>();
        points.add(new PointInfo("p1", 117.17, 31.52));
        points.add(new PointInfo("p2", 117.02, 30.31));
        points.add(new PointInfo("p3", 116.47, 33.57));
        points.add(new PointInfo("p4", 116.58, 33.38));
        points.add(new PointInfo("p5", 115.48, 32.54));
        points.add(new PointInfo("p6", 117.21, 32.56));
        points.add(new PointInfo("p7", 118.18, 29.43));
    }

    @Test
    public void test01() {
        String key = "GEO_POINTS";
        GeoOperations<String, String> ops = redisTemplate.opsForGeo();
        Set<RedisGeoCommands.GeoLocation<String>> locations = new HashSet<>();
        points.forEach(ci -> locations.add(new RedisGeoCommands.GeoLocation<String>(
                ci.getName(), new Point(ci.getLongitude(), ci.getLatitude())
        )));
        redisTemplate.opsForGeo().add(key, locations); // 保存坐标

        List<Point> list = ops.position(key, new String[]{"p1"}); //取坐标
        System.out.printf("(x, y)=(%s, %s)\n", list.get(0).getX(), list.get(0).getY());

        // 两点间距离
        Distance distance = ops.distance(key, "p1", "p2", Metrics.KILOMETERS);
        System.out.println("p1 和 p2 相距 " + distance.getValue() + " km");

        // p1 为圆心，半径200km 范围内的点
        Point point = new Point(points.get(0).getLongitude(), points.get(0).getLatitude());
        System.out.println(point);
        Distance radius = new Distance(200, Metrics.KILOMETERS);
        Circle circle = new Circle(point, radius);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = ops.radius(key, circle, GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().limit(5).sortAscending());
        results.forEach(p-> System.out.println(p.getContent().getName() + " " + p.getDistance()));

        // p3 为圆心，半径200km 范围内的点
        GeoResults<RedisGeoCommands.GeoLocation<String>> results1 = ops.radius(key, "p3", radius, GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().limit(5).sortAscending());
        results1.forEach(p-> System.out.println(p.getContent().getName() + " " + p.getDistance()));

        // hash值
        List<String> list1 = ops.hash(key, Arrays.asList("p4", "p5", "p7").toArray(new String[3]));
        System.out.println(list1);
        redisTemplate.delete(key);
    }

}