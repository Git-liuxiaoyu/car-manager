package com.woniu.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReportCarPo {
    private Integer id;
    private Integer carId;
    private String carNum;
    private BigDecimal totalMoney;
    private BigDecimal oilFee;
    private BigDecimal repairFee;
    private BigDecimal chargesFee;
    private BigDecimal keepFee;
    private BigDecimal violationFee;
    private BigDecimal accidentFee;
    private BigDecimal yearCheckFee;
    private BigDecimal insureFee;

}
