package com.uwjx.mongo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.uwjx.mongo.dto.SalaryDTO;
import com.uwjx.mongo.service.SalaryService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wang Huan
 * @author 18501667737@163.com
 * @date 2020/12/23 15:44
 */
@Slf4j
public class SalaryAnalysisEventListener extends AnalysisEventListener<Map<Integer , String>> {

    private final static Integer BATCH_SIZE = 5;

    private List<Map<Integer , String>> salaryList = new ArrayList<>();

    private SalaryService salaryService;

    public SalaryAnalysisEventListener(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        log.warn("解析工资 invoke -> {}" , JSON.toJSONString(integerStringMap));
        salaryList.add(integerStringMap);
        if(salaryList.size() > BATCH_SIZE){
            saveData();
            salaryList.clear();
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.warn("表头信息:{}" , JSON.toJSONString(headMap));
        salaryService.setHeadMap(headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.warn("解析工资 doAfterAllAnalysed @@@@@@@@@@@");

        saveData();

        salaryService.processData();
    }

    private void saveData(){
        salaryService.add(salaryList);
    }
}
