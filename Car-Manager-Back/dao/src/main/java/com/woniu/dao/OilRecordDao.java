package com.woniu.dao;

import com.woniu.po.DepartureRecordPo;
import com.woniu.po.OilRecordPo;

import java.util.List;

public interface OilRecordDao {


    //查询加油信息
    List<OilRecordPo> findList();

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
}
