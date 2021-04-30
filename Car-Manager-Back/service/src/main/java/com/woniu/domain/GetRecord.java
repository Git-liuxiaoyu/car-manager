package com.woniu.domain;

import com.woniu.po.OppositeCompanyPo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 取车记录实体类
 */
//@Data
public class GetRecord {
    private Integer id;
    private Integer carId;//车牌
    private Integer oppositeCompanyId;//修理厂id

    private OppositeCompanyPo ocpo;//修理厂对象

    private Integer reapairType;//修理类别
    private String sendTime;//送车时间
    private String getTime;//取车时间
    private BigDecimal cost;//花费金额
    private String repairItem;//维修项目
    private String material;//维修材料
    private String remarks;//取车备注


    public GetRecord() {
    }

    public GetRecord(Integer id, Integer carId, Integer oppositeCompanyId, OppositeCompanyPo ocpo, Integer reapairType, String sendTime, String getTime, BigDecimal cost, String repairItem, String material, String remarks) {
        this.id = id;
        this.carId = carId;
        this.oppositeCompanyId = oppositeCompanyId;
        this.ocpo = ocpo;
        this.reapairType = reapairType;
        this.sendTime = sendTime;
        this.getTime = getTime;
        this.cost = cost;
        this.repairItem = repairItem;
        this.material = material;
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getOppositeCompanyId() {
        return oppositeCompanyId;
    }

    public void setOppositeCompanyId(Integer oppositeCompanyId) {
        this.oppositeCompanyId = oppositeCompanyId;
    }

    public OppositeCompanyPo getOcpo() {
        return ocpo;
    }

    public void setOcpo(OppositeCompanyPo ocpo) {
        this.ocpo = ocpo;
    }

    public Integer getReapairType() {
        return reapairType;
    }

    public void setReapairType(Integer reapairType) {
        this.reapairType = reapairType;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getRepairItem() {
        return repairItem;
    }

    public void setRepairItem(String repairItem) {
        this.repairItem = repairItem;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "GetRecordPo{" +
                "id=" + id +
                ", carId=" + carId +
                ", oppositeCompanyId=" + oppositeCompanyId +
                ", ocpo=" + ocpo +
                ", reapairType=" + reapairType +
                ", sendTime='" + sendTime + '\'' +
                ", getTime='" + getTime + '\'' +
                ", cost=" + cost +
                ", repairItem='" + repairItem + '\'' +
                ", material='" + material + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
