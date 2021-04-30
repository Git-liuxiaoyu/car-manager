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
    private String insureNum;
    private String beginDate;
    private Integer type;
    private BigDecimal cost;
    private Integer oppositeCompanyId;
    private String outDate;
    private Integer driverId;
    private String  remarks;
}
