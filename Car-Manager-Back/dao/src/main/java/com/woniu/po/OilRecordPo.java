package com.woniu.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OilRecordPo {

    private Integer id;
    private Integer carId;
    private Integer oppositeCompanyId;
    private String addTime;
    private Integer oilType;
    private BigDecimal prePrice;
    private Double thisOilVolume;
    private Double lastOilVolume;
    private BigDecimal price;
    private Double thisMileage;
    private Double lastMileage;
    private String remarks;
    private Integer driverId;
}
