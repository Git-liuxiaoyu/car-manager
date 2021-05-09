package com.woniu.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Administrator
 * @Date 2021/4/30 11:41
 */
@Data
public class InsureRecord {
    private Integer id;
    private Integer cardId;
    private String carNum;//车牌号外键

    private String insureNum;
    private String beginDate;
    private Integer type;
    private String typename;//保险种类名


    private BigDecimal cost;
    private Integer oppositeCompanyId;
    private String oppositename;//保险公司名

    private String outDate;
    private Integer driverId;
    private String drivername;//经办人名

    private String  remarks;
}
