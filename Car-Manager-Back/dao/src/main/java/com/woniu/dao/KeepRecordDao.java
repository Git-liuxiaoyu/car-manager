package com.woniu.dao;

import com.woniu.po.KeepRecordPo;

import java.util.List;

public interface KeepRecordDao {
    //保养记录列表
    List<KeepRecordPo> list();

    //添加保养记录
    int add(KeepRecordPo keepRecordPo);

    //删除保养记录
    int delete(KeepRecordPo keepRecordPo);

    //修改保养记录
    int update(KeepRecordPo keepRecordPo);

    //根据id查询保养记录
    KeepRecordPo findbyid(KeepRecordPo keepRecordPo);
}
