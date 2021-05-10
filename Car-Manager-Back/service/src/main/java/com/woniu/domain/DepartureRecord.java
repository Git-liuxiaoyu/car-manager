package com.woniu.domain;

import lombok.Data;

@Data
public class DepartureRecord {
    private Integer id;
    private Integer deptId;
    private Integer carId;
    private String outTime;
    private String preInTime;
    private String inTime;
    private String followPerson;
    private Integer driverId;
    private String destination;
    private Double mileage;
    private Integer employeeId;
    private String outCarReason;
}
