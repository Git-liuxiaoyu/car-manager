package com.woniu.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Administrator
 * @Date 2021/4/30 10:30
 */
@Data
public class AccidentRecordPo {
    private Integer id;
    private Integer carId;
    private String carNum;
    private Integer driverId;
    private String driverName;
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
//    private CarPo carPo;
}
