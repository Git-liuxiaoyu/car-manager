package com.woniu.dao;

import com.woniu.po.*;

import java.util.List;

public interface OilRecordDao {


    //查询加油信息
    List<OilRecordPo> findList(String searchText);

    //添加加油信息
    int add(OilRecordPo oilRecordPo);
    //
    //修改加油信息
    int update(OilRecordPo oilRecordPo);
    //
    // 删除加油信息
    int delete(Integer id);
    //根据id查寻
    OilRecordPo getById(Integer id);

    //总数
    int count(String searchText);

    //查询车牌号
    List<CarPo> carlist();

    //查询往来单位为加油站的下拉框
    List<OppositeCompanyPo> oppolist(Integer type);

    //查询加油人员下拉框
    List<DriverPo> driverlist();


    //根据id查询
    OilRecordPo findbyid(OilRecordPo ocpo);


}
