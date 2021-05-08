package com.woniu.dao;

import com.woniu.po.ViolationRecordPo;

import java.util.List;

public interface ViolationRecordDao {


    //查询
    List<ViolationRecordPo> list(String searchText);

    //添加
    void add(ViolationRecordPo violationRecordPo);
    //
    //修改
    void update(ViolationRecordPo violationRecordPo);
    //计数
    Integer count(String searchText);
    // 删除
    void delete(Integer id);
}
