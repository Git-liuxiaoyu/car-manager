package com.woniu.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Administrator
 * @Date 2021/4/30 11:08
 */
@Data
public class AccidentRecord {
    private Integer id;
    private Integer carId;
    private Integer driverId;
    private String time;
    private String place;
    private String explain;
    private String selfCondition;
    private String oppoCondition;
    private String solveResult;
    private BigDecimal selfCost;
    private BigDecimal oppoCost;
    private BigDecimal insureCost;
    private String remarks;
}
