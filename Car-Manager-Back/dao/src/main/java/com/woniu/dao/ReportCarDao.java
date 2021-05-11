package com.woniu.dao;

import com.woniu.po.ReportCarPo;

import java.util.List;

public interface ReportCarDao {

    List<ReportCarPo> getAll();

    ReportCarPo getOne(Integer carId);
}
