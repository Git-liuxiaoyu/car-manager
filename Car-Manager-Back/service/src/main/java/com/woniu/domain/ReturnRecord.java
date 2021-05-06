package com.woniu.domain;

import lombok.Data;

@Data
public class ReturnRecord {
    private Integer id;
    private Integer carId;
    private Integer driverId;
    private String outTime;
    private String inTime;
    private Double startMileage;
    private Double returnMileage;
    private String stopPosition;
    private String remarks;

}