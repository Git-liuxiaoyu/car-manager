package com.woniu.service;


import com.woniu.domain.OilRecord;
import com.woniu.domain.RepairRecord;
import com.woniu.po.RepairRecordPo;

import java.util.List;

public interface RepairRecordService {

    //查询维修信息
    List<RepairRecord> list(String searchText,int pageIndex,int pageSize);

    //添加维修信息
    void add(RepairRecord repairRecord);
    //
    //修改维修信息
    void update(RepairRecord repairRecord);

    int count(String searchText);
    // 删除维修信息
    void delete(Integer id);

}
