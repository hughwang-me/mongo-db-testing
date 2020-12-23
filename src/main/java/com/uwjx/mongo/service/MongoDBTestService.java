package com.uwjx.mongo.service;

import com.alibaba.fastjson.JSON;
import com.uwjx.mongo.entity.AccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@Slf4j
public class MongoDBTestService {

    @Autowired
    MongoTemplate mongoTemplate;

    public void save(){

        String dbName = mongoTemplate.getDb().getName();
        log.warn("dbName : {}" , dbName);

        for (int i = 0; i < 10; i++) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setId(String.valueOf(1000 + i));
            accountEntity.setName("wanghuan" + i);
            accountEntity.setPassword("9999991");
            mongoTemplate.save(accountEntity);
        }



//        AccountEntity accountEntityQueryResult = mongoTemplate.findOne(new Query(where("name").is("wanghuan")) , AccountEntity.class);
//        log.warn("query result -> {}" , JSON.toJSONString(accountEntityQueryResult) );
//
//        mongoTemplate.remove(new Query(where("name").is("wanghuan")) , AccountEntity.class);
    }

    public void delete(){
        mongoTemplate.getDb().getName();
    }

    public void edit(){

    }

    public void list(){
        Query query = new Query();
//        query.addCriteria(Criteria.where("g"))
//        query.skip(3);
//        query.limit(4);
//        query.with(Sort.by(
//           Sort.Order.asc("_id")
//        ));
        Sort sort = Sort.by(
          Sort.Order.asc("_id")
        );
        Pageable pageable = PageRequest.of(0,3 , sort);
        query.with( pageable);
        List<Map> accountEntityQueryResult = mongoTemplate.find(query , Map.class , "salary");
        log.warn("query result -> {}" ,accountEntityQueryResult.size());

        for (Map accountEntity : accountEntityQueryResult) {
            log.warn("id -> {}   工号 -> {}" , accountEntity.get("_id") , accountEntity.get("工号"));
        }
    }

    public void list2(){
        Query query = new Query();
        query.skip(3);
        query.limit(4);
        query.with(Sort.by(
                Sort.Order.asc("id")
        ));
        List<AccountEntity> accountEntityQueryResult = mongoTemplate.find(query , AccountEntity.class , "accountEntity");
        log.warn("query result -> {}" ,accountEntityQueryResult.size());

        for (AccountEntity accountEntity : accountEntityQueryResult) {
            log.warn("id -> {}" , accountEntity.getName());
        }
    }
}
