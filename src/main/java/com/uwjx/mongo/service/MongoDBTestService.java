package com.uwjx.mongo.service;

import com.alibaba.fastjson.JSON;
import com.uwjx.mongo.entity.AccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@Slf4j
public class MongoDBTestService {

    @Autowired
    MongoTemplate mongoTemplate;

    public void save(){

        String dbName = mongoTemplate.getDb().getName();
        log.warn("dbName : {}" , dbName);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId("1001");
        accountEntity.setName("wanghuan");
        accountEntity.setPassword("9999991");

        mongoTemplate.save(accountEntity);

        AccountEntity accountEntityQueryResult = mongoTemplate.findOne(new Query(where("name").is("wanghuan")) , AccountEntity.class);
        log.warn("query result -> {}" , JSON.toJSONString(accountEntityQueryResult) );

        mongoTemplate.remove(new Query(where("name").is("wanghuan")) , AccountEntity.class);
    }

    public void delete(){
        mongoTemplate.getDb().getName();
    }

    public void edit(){

    }

    public void list(){
        AccountEntity accountEntityQueryResult = mongoTemplate.findOne(new Query(where("name").is("wanghuan2")) , AccountEntity.class);
        log.warn("query result -> {}" , JSON.toJSONString(accountEntityQueryResult) );
    }
}
