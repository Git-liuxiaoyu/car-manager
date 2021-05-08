package com.woniu.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KeepRecord {

    private Integer id;
    private Integer carId;//车牌
    private String carNum;//车牌号外键

    private Integer type;//保养类型
    private String typename;//保养类型名

    private String keepDate;//保养时间
    private BigDecimal fee;//保养金额
    private Integer oppositeCompanyId;//保养单位
    private String oppositename;//往来单位名

    private double thisKeepMileage;//本次保养里程
    private String keepContext;//保养内容
    private String nextKeepTime;//下次保养时间
    private double nextKeepMileage;//下次保养里程
    private Integer driverId;//经办人
    private String drivername;//经办人员名

    private String remarks;//保养备注
}
