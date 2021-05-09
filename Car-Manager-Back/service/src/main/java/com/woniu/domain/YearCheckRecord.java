package com.woniu.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Administrator
 * @Date 2021/4/29 17:58
 */
@Data
public class YearCheckRecord {
    private Integer id;
    private Integer carId;
    private String checkNum;
    private String checkDate;
    private BigDecimal cost;
    private Integer oppositeCompanyId;
    private String nextCheckDate;
    private Integer driverId;
    private String remarks;
    private String carNum;
}
