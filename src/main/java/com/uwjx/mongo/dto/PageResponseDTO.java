package com.uwjx.mongo.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Wang Huan
 * @author 18501667737@163.com
 * @date 2020/12/23 17:40
 */
@Data
public class PageResponseDTO {

    private Long pageSize;
    private Long pageIndex;
    private Long total;
    private List<Map> data;
}
