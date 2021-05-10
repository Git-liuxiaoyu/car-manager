package com.woniu.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Administrator
 * @Date 2021/4/29 17:58
 */
@Data
public class Car {
    private Integer id;
    private String carNum;
    private Integer brandId;
    private Integer type;
    private Integer color;
    private String carrying;
    private Integer seatNum;
    private BigDecimal oilConsume;
    private BigDecimal initialMileage;
    private BigDecimal curingMileage;
    private Integer curingCycle;
    private String engineNum;
    private String serialNumber;
    private Integer oppositeCompanyId;
    private BigDecimal price;
    private String boughtDate;
    private Integer deptId;
    private Integer carStatus;
    private String carStatusname;//车辆状态名字
    private String remarks;
    private Integer status;
    private String images;
}
