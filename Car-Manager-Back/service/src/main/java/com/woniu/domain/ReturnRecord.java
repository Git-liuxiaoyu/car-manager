package com.woniu.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReturnRecord {
    private Integer id;
    private Integer dictionaryId;
    private String returnMileage;
    private String stopPosition;
    private String remarks;
    private BigDecimal zongMileage;//总里程
    private String mileage;//本次里程
    private String inCarTime;//回车时间
    private Integer carId;
}
