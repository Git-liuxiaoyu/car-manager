package com.woniu.po;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 违章记录实体类
 */
@Data
public class ViolationRecordPo {

    private Integer id;//id
    private Integer carId;//车牌
    private Integer type;//违章项目
    private String time;//违章时间
    private BigDecimal fee;//违章罚款
    private Integer reduceScore;//违章扣分
    private Integer driverId;//驾驶员id
    private String place;//违章地点
    private String remarks;//违章备注
    private String carNum; //车牌号码
    private String driverName; // 驾驶员姓名

}
