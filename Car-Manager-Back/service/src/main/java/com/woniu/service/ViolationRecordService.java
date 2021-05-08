package com.woniu.service;


import com.woniu.domain.ViolationRecord;

import java.util.List;

public interface ViolationRecordService {

    //查询维修信息
    List<ViolationRecord> list(String searchText,int pageIndex,int pageSize);

    //添加维修信息
    void add(ViolationRecord violationRecord);
    //
    //修改维修信息
    void update(ViolationRecord violationRecord);

    int count(String searchText);
    // 删除维修信息
    void delete(Integer id);

}
