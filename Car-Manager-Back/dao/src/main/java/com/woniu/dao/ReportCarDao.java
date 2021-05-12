package com.woniu.dao;

import com.woniu.po.ReportCarPo;

import java.util.List;

public interface ReportCarDao {

    List<ReportCarPo> getAll();

    ReportCarPo getOne(Integer carId);
    //查询出车次数
    Integer getDepartureNum(Integer carId);
}
