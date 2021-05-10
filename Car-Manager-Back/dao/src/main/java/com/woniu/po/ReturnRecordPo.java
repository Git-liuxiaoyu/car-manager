package com.woniu.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReturnRecordPo {
    private Integer id;
    private Integer dictionaryId;
    private String returnMileage;//回车里程
    private String stopPosition;
    private String remarks;
    private String mileage;//出车本次里程
    private BigDecimal zongMileage;//总里程
    private String inCarTime;//回车时间
}
