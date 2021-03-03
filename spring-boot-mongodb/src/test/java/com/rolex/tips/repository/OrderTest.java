/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.repository;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.rolex.tips.model.Contact;
import com.rolex.tips.model.Location;
import com.rolex.tips.model.Order;
import org.bson.BsonInt32;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;

/**
 * @author rolex
 * @since 2019
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OrderTest {

    @Autowired
    MongoDatabase mongoDatabase;
    String[] name = {"Alice", "Bob", "Tom", "Jack", "John"};
    String[] phone = {"18600001111", "18600002345", "18600006789", "18600001357", "18600002468"};
    String[] coffee = {"Americano", "Latte", "Macchiato", "Cappuccino", "Mocha"};


    @Test
    public void delete() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        col.deleteMany(new BasicDBObject());
    }

    @Test
    public void insert() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        Order order = new Order();
        order.setCustomerId(new Random().nextInt(5));
        order.setUserId(new Random().nextInt(5));
        order.setName(coffee[new Random().nextInt(5)]);
        order.setIdt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        order.setUdt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        order.setContact(new Contact(phone[new Random().nextInt(5)], name[new Random().nextInt(5)] + "@gmail.com", new Location(new Random().nextFloat() * 100, new Random().nextFloat() * 100)));
        String json = new Gson().toJson(order);
        Document document = Document.parse(json);
        col.insertOne(document);
    }

    @Test
    public void bulkInsert() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        List<Order> list = Lists.newArrayList();
        for (int i = 1; i < 20; i++) {
            Order order1 = new Order();
            order1.setCustomerId(new Random().nextInt(5) + 1);
            order1.setUserId(new Random().nextInt(5) + 1);
            order1.setName(coffee[new Random().nextInt(5)]);
            order1.setIdt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            order1.setUdt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            order1.setContact(new Contact("18600001111", "Tom@gmail.com", new Location(new Random().nextFloat() * 100, new Random().nextFloat() * 100)));
            list.add(order1);
        }

        col.insertMany(list.stream().map(o -> Document.parse(new Gson().toJson(o))).collect(Collectors.toList()));
    }

    @Test
    public void sortByUserDesc() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        BasicDBObject sortObject = new BasicDBObject("userId", -1); // 按userId倒序
        sortObject.put("_id", 1);
        MongoCursor<Document> cursor = col.find()
                .sort(sortObject)
                .iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            System.out.println(new Gson().toJson(document));
        }
    }

    @Test
    public void groupByCustomer() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        MongoCursor<Document> cursor = col.aggregate(Lists.newArrayList(
                group("$userId", sum("count", new BsonInt32(1))) // 按userId分组求count
        )).iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            System.out.println(new Gson().toJson(document));
        }
    }

    @Test
    public void groupByCustomerAndUser() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        MongoCursor<Document> cursor = col.aggregate(Lists.newArrayList(
                group(new BasicDBObject("customerId", "$customerId") // 按customerId和userId分组
                        .append("userId", "$userId"), sum("count", new BsonInt32(1)))
        )).iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            System.out.println(new Gson().toJson(document));
        }
    }

    @Test
    public void countByCustomerDistinctByUser() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        MongoCursor<Document> cursor = col.aggregate(Lists.newArrayList(
                match(eq("customerId", 1)),
                group(new BasicDBObject("customerId", "$customerId").append("userId", "$userId"), sum("count", new BsonInt32(1))),
                group("$_id.customerId", sum("count", new BsonInt32(1)))
        )).iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            System.out.println(new Gson().toJson(document));
        }
    }

    @Test
    public void countByCustomerDistinctByUser1() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        String query1 = "{$match:{\"customerId\": 1}}";
        String query2 = "{$group:{_id:{customerId:\"$customerId\",userId:\"$userId\"}}}";
        String query3 = "{$group:{_id:\"$_id.customerId\",count:{\"$sum\":1}}}";
        Bson bson1 = BasicDBObject.parse(query1);
        Bson bson2 = BasicDBObject.parse(query2);
        Bson bson3 = BasicDBObject.parse(query3);
        MongoCursor<Document> cursor = col.aggregate(Lists.newArrayList(bson1, bson2, bson3)).iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            System.out.println(new Gson().toJson(document));
        }
    }

    @Test
    public void findAll() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        long total = col.countDocuments();
        int page = 0;
        int pageSize = 3;
        long totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        while (page < totalPage) {
            int skip = page * pageSize;
            MongoCursor<Document> cursor = col.find()
                    .skip(skip)
                    .limit(pageSize)
                    .iterator();
            Gson gson = new Gson();
            System.out.println("第" + (page + 1) + "页");
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String objectId = document.get("_id").toString();
                String json = gson.toJson(document);
                Order order = gson.fromJson(json, Order.class);
                order.setObjectId(objectId);
                System.out.println(order);
            }
            page++;
        }
    }

    @Test
    public void findAll1() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        long total = col.countDocuments();
        String head = null;
        int pageSize = 3;
        int page = 1;
        long totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        MongoCursor<Document> cursor = col.find()
                .skip(0)
                .limit(pageSize)
                .iterator();

        Gson gson = new Gson();
        System.out.println("第1页");
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String objectId = document.get("_id").toString();
            Order order = gson.fromJson(document.toJson(), Order.class);
            order.setObjectId(objectId);
            head = order.getObjectId();
            System.out.println(order);
        }

        while (page < totalPage) {
            System.out.println("第" + (page + 1) + "页");
            cursor = col.find(and(gt("_id", new ObjectId(head))))
                    .batchSize(pageSize)
                    .limit(pageSize)
                    .iterator();

            while (cursor.hasNext()) {
                Document document = cursor.next();
                String objectId = document.get("_id").toString();
                Order order = gson.fromJson(document.toJson(), Order.class);
                order.setObjectId(objectId);
                head = order.getObjectId();
                System.out.println(order);
            }

            page++;
        }
    }

    @Test
    public void cursor() {
        MongoCollection<Document> col = mongoDatabase.getCollection("order");
        String start = LocalDateTime.of(1970, 1, 1, 0, 0, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String end = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Bson bson = and(gte("idt", start), lt("idt", end));
        FindIterable<Document> findIterable = col.find(bson);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        int i = 0;
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
            i++;
        }
        System.out.println("i=" + i);
    }
}
