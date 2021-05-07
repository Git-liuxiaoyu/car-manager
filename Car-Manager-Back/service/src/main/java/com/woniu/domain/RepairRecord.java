package com.woniu.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RepairRecord {

     private Integer id;
     private Integer carId;
     private Integer oppositeCompanyId;
     private String sendTime;
     private String preGetTime;
     private String reason;
     private String remarks;
     private Integer driverId;
     private String getTime;
     private BigDecimal repairFee;
     private Integer repairType;
     private String repairItem;
     private String useGoods;
     private String carNum;
}
