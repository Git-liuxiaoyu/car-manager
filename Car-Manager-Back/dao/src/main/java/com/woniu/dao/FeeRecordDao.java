package com.woniu.dao;

import com.woniu.po.CarPo;
import com.woniu.po.FeeRecordPo;
import com.woniu.po.GetRecordPo;

import java.util.List;

public interface FeeRecordDao {

    //取车记录列表
    List<FeeRecordPo> list(String searchText);

    //添加取车记录
    int add(FeeRecordPo feeRecordPo);

    //删除取车记录
    int delete(FeeRecordPo feeRecordPo);

    //修改取车记录
    int update(FeeRecordPo feeRecordPo);

    //根据id查询记录
    FeeRecordPo findbyid(FeeRecordPo feeRecordPo);

    int count(String searchText);

    //获取车辆档案
    List<CarPo> getAllCar();
}
