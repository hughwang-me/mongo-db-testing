package com.uwjx.mongo.service;

import com.uwjx.mongo.dto.SalaryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author Wang Huan
 * @author 18501667737@163.com
 * @date 2020/12/23 16:05
 */
@Service
@Scope("prototype")
@Slf4j
public class SalaryService {

    @Autowired
    MongoTemplate mongoTemplate;

    private String salaryMonth;

    private Map<Integer, String> headMap = new HashMap<>();

    private List<Map<Integer , String>> salaryList = new ArrayList<>();

    private List<Map<String , String>> salaryResult = new ArrayList<>();

    public void add(List<Map<Integer , String>> salaryList){
        this.salaryList.addAll(salaryList);
    }

    public void setHeadMap(Map<Integer, String> headMap) {
        this.headMap = headMap;
    }

    public void setSalaryMonth(String salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public Integer getDataSize(){
        return salaryList.size();
    }

    public void processData(){
        log.warn("开始处理工资数据");
        log.warn("工资总条数:{}" ,  getDataSize());

        for (Map<Integer, String> integerStringMap : salaryList) {
            Map<String , String> salaryItem = new HashMap<>();
            integerStringMap.forEach((index , value) ->{
                salaryItem.put(headMap.get(index) , value);
            });
            salaryResult.add(salaryItem);
            salaryItem.put("工资月份" , salaryMonth);

            Query criteria  = Query.query(Criteria.where("工号").is(salaryItem.get("工号")).andOperator(Criteria.where("工资月份").is(salaryItem.get("工资月份"))));

            mongoTemplate.remove(criteria , SalaryDTO.class ,"salary");

            mongoTemplate.save(salaryItem , "salary");
        }

        log.warn("共处理了{}条工资记录" , salaryResult.size());
    }
}
