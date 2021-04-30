package com.woniu.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeRecord {

    private Integer id;
    private Integer carId;
    private String feeName;
    private String payTime;
    private BigDecimal fee;
    private Integer oppositeCompanyId;
    private Integer driverId;
    private String remarks;
}
