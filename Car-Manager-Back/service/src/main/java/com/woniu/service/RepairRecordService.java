package com.woniu.service;


import com.woniu.domain.OilRecord;
import com.woniu.domain.RepairRecord;
import com.woniu.po.RepairRecordPo;

import java.util.List;

public interface RepairRecordService {

    //查询维修信息
    List<RepairRecord> findList();

    //添加维修信息
    int add(RepairRecord repairRecord);
    //
    //修改维修信息
    int update(RepairRecord repairRecord);
    //
    // 删除维修信息
    int delete(Integer id);

}
