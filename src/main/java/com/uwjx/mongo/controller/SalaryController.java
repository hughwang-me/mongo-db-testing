package com.uwjx.mongo.controller;

import com.alibaba.fastjson.JSON;
import com.mongodb.MongoException;
import com.uwjx.mongo.dto.PageResponseDTO;
import com.uwjx.mongo.dto.SalaryDTO;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Wang Huan
 * @author 18501667737@163.com
 * @date 2020/12/23 16:57
 */
@RestController
@RequestMapping(value = "salary")
@Slf4j
public class SalaryController {

    @Autowired
    MongoTemplate mongoTemplate;

    @PostMapping(value = "query")
    public String listSalary(@RequestBody SalaryDTO salaryDTO){
        log.warn("查询条件:{}" , JSON.toJSONString(salaryDTO));
        Query query  = Query.query(Criteria.where("工号").is(salaryDTO.getStaffId())
                .andOperator(Criteria.where("工资月份").is(salaryDTO.getSalaryMonth())));

        DocumentCallbackHandler callbackHandler = new DocumentCallbackHandler() {
            @Override
            public void processDocument(Document document) throws MongoException, DataAccessException {
                log.warn("查询结果:{}" , JSON.toJSONString(document));
            }
        };
//        mongoTemplate.executeQuery(query , "salary" , callbackHandler);


//        Document query = new Document();
//
//        query.append("工号", salaryDTO.getStaffId());
//        query.append("工资月份" , salaryDTO.getSalaryMonth());
//
//        Consumer<Document> processBlock = new Consumer<Document>() {
//            @Override
//            public void accept(Document document) {
//                log.warn("结果:{}" , document);
//            }
//        };
//
//        mongoTemplate.getCollection("salary").find(query).forEach(processBlock);

        List<Map> mapList = mongoTemplate.find(query , Map.class , "salary");

        return JSON.toJSONString(mapList);
    }

    @PostMapping(value = "page")
    public PageResponseDTO page(@RequestBody SalaryDTO salaryDTO){
        log.warn("查询条件:{}" , JSON.toJSONString(salaryDTO));
        PageResponseDTO responseDTO = new PageResponseDTO();
        Query query  = new Query();


        boolean isHasCriteria = false;
        if(StringUtils.hasLength(salaryDTO.getStaffId())){
            query.addCriteria(Criteria.where("工号").is(salaryDTO.getStaffId()));
            isHasCriteria = true;
        }
        if(StringUtils.hasLength(salaryDTO.getSalaryMonth())){
            query.addCriteria(Criteria.where("工资月份").is(salaryDTO.getSalaryMonth()));
            isHasCriteria = true;
        }
        List<Map> mapList = null;
//        if(isHasCriteria){
//            mongoTemplate.find(query , Map.class , "salary");
//        }else {
//            mongoTemplate.findAll(Map.class , "salary");
//        }

        mongoTemplate.find(query , Map.class , "salary");

        responseDTO.setData(mapList);
        return responseDTO;
    }
}
