package com.woniu.service;


import com.woniu.domain.ViolationRecord;

import java.util.List;

public interface ViolationRecordService {

    // 查询
    List<ViolationRecord> list(String searchText,int pageIndex,int pageSize);

    // 添加
    void add(ViolationRecord violationRecord);
    // 修改
    void update(ViolationRecord violationRecord);
    // 计数
    int count(String searchText);
    // 删除
    void delete(Integer id);

}
