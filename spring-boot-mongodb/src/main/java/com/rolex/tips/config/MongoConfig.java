package com.rolex.tips.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
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
public class MongoConfig {

    @Value("${spring.data.mongodb.host}")
    String host;
    @Value("${spring.data.mongodb.port}")
    int port;
    @Value("${spring.data.mongodb.database}")
    String database;

    @Bean
    public MongoDatabase mongoDatabase() {
        MongoDatabase database = mongoClient().getDatabase(this.database);
        return database;
    }

    @Bean
    public MongoClient mongoClient() {
        MongoClientURI connectionString = new MongoClientURI("mongodb://" + host + ":" + port);
        MongoClient mongoClient = new MongoClient(connectionString);
        return mongoClient;
    }
}
