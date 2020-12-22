package com.uwjx.mongo.service;

import com.mongodb.*;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class MongoCurdService implements ApplicationContextAware , InitializingBean {

    MongoClient mongoClient;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.warn("setApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.warn("afterPropertiesSet");

        ServerAddress serverAddress = new ServerAddress("uwjx.com");


        MongoClientOptions clientOptions = MongoClientOptions.builder()
                .build();

        MongoCredential credential = MongoCredential.createCredential(
                "admin",
                "admin",
                "".toCharArray()
        );
        mongoClient = new MongoClient(serverAddress , credential,clientOptions);

        MongoIterable<String> dbs = mongoClient.listDatabaseNames();
        MongoCursor<String> dbCursor = dbs.iterator();
        while (dbCursor.hasNext()){
            String dbName = dbCursor.next();
            log.warn("db : {}" , dbName);
        }

        MongoDatabase database = mongoClient.getDatabase("admin");
        MongoIterable<String>  mongoIterable = database.listCollectionNames();
        MongoCursor<String> collectionCursor = mongoIterable.cursor();
        while (collectionCursor.hasNext()){
            String collection  = collectionCursor.next();
            log.warn("collection -> {}" , collection);
        }


    }
}
