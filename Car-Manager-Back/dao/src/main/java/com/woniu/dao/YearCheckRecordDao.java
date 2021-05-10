package com.woniu.dao;

import com.woniu.po.YearCheckRecordPo;

import java.util.List;

public interface YearCheckRecordDao {


    //查询
    List<YearCheckRecordPo> list(String searchText);

    //添加
    void add(YearCheckRecordPo yearCheckRecordPo);
    //
    //修改
    void update(YearCheckRecordPo yearCheckRecordPo);
    //计数
    Integer count(String searchText);
    // 删除
    void delete(Integer id);

    YearCheckRecordPo findbyid(YearCheckRecordPo yearCheckRecordPo);
}
