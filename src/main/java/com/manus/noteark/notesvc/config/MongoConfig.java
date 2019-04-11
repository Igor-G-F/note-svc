package com.manus.noteark.notesvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.manus.noteark.notesvc.repository")
public class MongoConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "noteark";
    }
  
    @Override
    public MongoClient mongoClient() {
        return new MongoClient("localhost", 27017);
    }
  
    @Override
    protected String getMappingBasePackage() {
        return "com.manus.noteark.notesvc";
    }
}