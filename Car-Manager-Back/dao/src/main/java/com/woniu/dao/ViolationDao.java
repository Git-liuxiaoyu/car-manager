package com.woniu.dao;

import com.woniu.po.ViolationRecordPo;

import java.util.List;

public interface ViolationDao {

    //违章记录列表
    List<ViolationRecordPo> list();

    //添加违章记录
    int add(ViolationRecordPo vrpo);

    //删除违章记录
    int delete(ViolationRecordPo vrpo);

    //修改违章记录
    int update(ViolationRecordPo vrpo);

    //根据id查询违章记录
    ViolationRecordPo findbyid(ViolationRecordPo vrpo);
}
