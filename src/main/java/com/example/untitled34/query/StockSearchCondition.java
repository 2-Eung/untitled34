package com.example.untitled34.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockSearchCondition {     // 검색을 좀 더 디테일하게 하기위해 만듬
    private String productName;
    private String warehouseName;
    private Integer minQuantity;
    private Integer maxQuantity;
}