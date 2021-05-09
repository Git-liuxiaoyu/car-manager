package com.woniu.dao;

import com.woniu.po.DepartureRecordPo;
import com.woniu.po.DriverPo;

import java.util.List;

public interface DepartureRecordDao {


    //查询出车信息
    List<DepartureRecordPo> findList();

    //添加出车信息
    int add(DepartureRecordPo departureRecordPo);
    //
    //修改出车信息
    int update(DepartureRecordPo departureRecordPo);
    //
    // 删除出车信息
    int delete(Integer id);
    //根据id查寻
    DepartureRecordPo getById(Integer id);

    Integer count(String searchText);

    List<DepartureRecordPo> findList(String searchText);
}
