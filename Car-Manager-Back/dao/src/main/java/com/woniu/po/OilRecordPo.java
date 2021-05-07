package com.woniu.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OilRecordPo {

    private Integer id;
    private Integer carId;
    private String carNum;//车牌号外键
    private Integer oppositeCompanyId;
    private String oppositename;//往来单位名
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
    private String drivername;//加油人员名
}
