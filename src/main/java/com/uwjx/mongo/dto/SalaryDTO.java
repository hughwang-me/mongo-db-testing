package com.uwjx.mongo.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


/**
 * @author SalleyGardens
 */
@Data
public class SalaryDTO {

    private Object id;

    private String staffId;
    private String salaryMonth;

    private Long pageIndex;
    private Long pageSize;
}
