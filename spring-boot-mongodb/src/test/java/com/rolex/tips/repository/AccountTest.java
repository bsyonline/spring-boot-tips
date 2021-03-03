package com.rolex.tips.repository;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.rolex.tips.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Updates.inc;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AccountTest {

    @Autowired
    MongoClient mongoClient;

    @BeforeEach
    public void init() {
        insert(1, 100);
        insert(2, 100);
    }

    @Test
    public void transfer() {

        int from = 1;
        int to = 2;
        long amount = 200;

        MongoCollection<Document> col = mongoClient.getDatabase("test").getCollection("account");
        ClientSession clientSession = mongoClient.startSession();
        try {
            col.updateOne(eq("id", from), inc("amount", amount));
            col.updateOne(and(eq("id", to), gte("amount", amount)), inc("amount", -amount));
            clientSession.commitTransaction();
        } catch (Exception e) {
            clientSession.abortTransaction();
        }
    }


    public void insert(Integer id, long amount) {
        MongoCollection<Document> col = mongoClient.getDatabase("test").getCollection("account");
        Account account = new Account();
        account.setAmount(amount);
        account.setId(id);
        col.insertOne(Document.parse(new Gson().toJson(account)));
        log.info("add {} to account {}, balance is {}", amount, id, amount);
    }
}
