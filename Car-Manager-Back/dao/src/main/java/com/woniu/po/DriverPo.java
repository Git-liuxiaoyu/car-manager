package com.woniu.po;

import lombok.Data;

@Data
public class DriverPo {
    private Integer id;
    private String driverNum;
    private Integer type;
    private Integer status;
    private Integer employeeId;
    private String employeeName;
    private String remarks;
    private EmployeePo employeePo;


}
