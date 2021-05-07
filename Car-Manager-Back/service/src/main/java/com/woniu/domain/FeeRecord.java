package com.woniu.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeRecord {

    private Integer id;
    private Integer carId;
    private Car car;
    private String feeName;
    private String payTime;
    private BigDecimal fee;
    private Integer oppositeCompanyId;
    private String oppositeName;
    private Integer driverId;
    private String driverName;
    private String remarks;
}
