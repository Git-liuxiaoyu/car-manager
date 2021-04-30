package com.woniu.po;

import lombok.Data;

import java.util.Date;

@Data
public class DepartureRecordPo {

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


}
