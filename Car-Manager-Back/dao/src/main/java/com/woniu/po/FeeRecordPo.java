package com.woniu.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 规费记录
 */
@Data
public class FeeRecordPo {
    private Integer id;
    private Integer carId;//车牌
    private String feeName;//规费名称
    private String payTime;//缴费时间
    private BigDecimal fee;//规费金额
    private Integer oppositeCompanyId;//收费单位
    private Integer driverId;//经办人
    private String remarks;//备注

}
