package com.uwjx.mongo;

import com.uwjx.mongo.service.MongoDBTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MongoDbTestingApplicationTests {

    @Autowired
    MongoDBTestService service;

    @Test
    void contextLoads() {

        service.list();
    }

}
