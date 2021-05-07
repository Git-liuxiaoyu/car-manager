package com.woniu.dao;

import com.woniu.po.RepairRecordPo;

import java.util.List;

public interface RepairRecordDao {


    //查询维修信息
    List<RepairRecordPo> list(String searchText);

    //添加维修信息
    void add(RepairRecordPo repairRecordPo);
    //
    //修改维修信息
    void update(RepairRecordPo repairRecordPo);
    //
    Integer count(String searchText);

    void delete(Integer id);
}
