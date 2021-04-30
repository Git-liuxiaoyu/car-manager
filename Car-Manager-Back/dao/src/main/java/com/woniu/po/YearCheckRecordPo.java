package com.woniu.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Administrator
 * @Date 2021/4/29 17:58
 */
@Data
public class YearCheckRecordPo {
    private Integer id;
    private Integer carId;
    private String checkNum;
    private String checkDate;
    private BigDecimal cost;
    private Integer oppositeCompanyId;
    private String nextCheckDate;
    private Integer driverId;
    private String remarks;
}
