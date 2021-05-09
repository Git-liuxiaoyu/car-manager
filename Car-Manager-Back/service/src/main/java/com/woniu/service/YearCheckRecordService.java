package com.woniu.service;


import com.woniu.domain.YearCheckRecord;

import java.util.List;

public interface YearCheckRecordService {

    // 查询
    List<YearCheckRecord> list(String searchText,int pageIndex,int pageSize);

    // 添加
    void add(YearCheckRecord yearCheckRecord);
    // 修改
    void update(YearCheckRecord yearCheckRecord);
    // 计数
    int count(String searchText);
    // 删除
    void delete(Integer id);

}
