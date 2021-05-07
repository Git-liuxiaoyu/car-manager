package com.woniu.domain;

import lombok.Data;

@Data
public class Driver {
    private Integer id;
    private String driverNum;
    private Integer type;
    private Integer status;
    private Integer employeeId;
    private String employeeName;
    private String remarks;
    private Employee employee;

}
