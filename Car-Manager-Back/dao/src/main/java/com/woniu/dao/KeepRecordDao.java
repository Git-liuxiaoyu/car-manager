package com.woniu.dao;

import com.woniu.po.KeepRecordPo;
import com.woniu.po.OilRecordPo;

import java.util.List;

public interface KeepRecordDao {
    //查询加油信息
    List<KeepRecordPo> findList(String searchText);

    //添加保养记录
    int add(KeepRecordPo keepRecordPo);

    //删除保养记录
    int delete(KeepRecordPo keepRecordPo);

    //修改保养记录
    int update(KeepRecordPo keepRecordPo);

    //根据id查询保养记录
    KeepRecordPo findbyid(KeepRecordPo keepRecordPo);

    //总数
    int count(String searchText);

}
