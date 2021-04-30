package com.woniu.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KeepRecordPo {

    private Integer id;
    private Integer carId;//车牌
    private Integer type;//保养类型
    private String keepDate;//保养时间
    private BigDecimal fee;//保养金额
    private Integer oppositeCompanyId;//保养单位
    private double thisKeepMileage;//本次保养里程
    private String keepContext;//保养内容
    private String nextKeepTime;//下次保养时间
    private double nextKeepMileage;//下次保养里程
    private Integer driverId;//经办人
    private String remarks;//保养备注

}
