package com.uwjx.mongo.controller;

import com.alibaba.excel.EasyExcel;
import com.uwjx.mongo.dto.SalaryDTO;
import com.uwjx.mongo.listener.SalaryAnalysisEventListener;
import com.uwjx.mongo.service.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Wang Huan
 * @author 18501667737@163.com
 * @date 2020/12/23 14:37
 */
@RestController
@Slf4j
public class ExcelController {

    @Autowired
    BeanFactory beanFactory;


    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file , @RequestParam("salaryMonth") String salaryMonth)  {
        try {
            log.warn("处理 {} 的工资" , salaryMonth);
            SalaryService salaryService = beanFactory.getBean(SalaryService.class);
            salaryService.setSalaryMonth(salaryMonth);
            EasyExcel.read(file.getInputStream(), new SalaryAnalysisEventListener(salaryService)).sheet("职工工资").doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
