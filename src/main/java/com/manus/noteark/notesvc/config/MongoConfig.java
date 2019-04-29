package com.manus.noteark.notesvc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.manus.noteark.notesvc.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${mongodb.host}")
    private String mongoHost;

    @Value("${mongodb.port}")
    private int mongoPort;

    @Override
    protected String getDatabaseName() {
        return "noteark";
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(mongoHost, mongoPort);
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.manus.noteark.notesvc";
    }
}
