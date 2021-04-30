package com.woniu.service;

import com.woniu.domain.GetRecord;
import com.woniu.po.GetRecordPo;

import java.util.List;

public interface GetRecordService {

    //取车记录列表
    List<GetRecord> list();

    //添加取车记录
    int add(GetRecordPo getRecordPo);

    //删除取车记录
    int delete(GetRecordPo getRecordPo);

    //修改取车记录
    int update(GetRecordPo getRecordPo);

    //根据id查询记录
    GetRecordPo findbyid(GetRecordPo getRecordPo);
}
