package com.woniu.dao;

import com.woniu.po.RepairRecordPo;

import java.util.List;

public interface RepairRecordDao {


    //查询维修信息
    List<RepairRecordPo> findList();

    //添加维修信息
    int add(RepairRecordPo repairRecordPo);
    //
    //修改维修信息
    int update(RepairRecordPo repairRecordPo);
    //
    // 删除维修信息
    int delete(Integer id);
}
