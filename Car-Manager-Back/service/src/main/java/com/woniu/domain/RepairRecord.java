package com.woniu.domain;

import lombok.Data;

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
}
