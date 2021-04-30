package com.woniu.service;

import com.woniu.domain.FeeRecord;
import com.woniu.domain.GetRecord;
import com.woniu.po.FeeRecordPo;
import com.woniu.po.GetRecordPo;

import java.util.List;

public interface FeeRecordService {

    //规费记录列表
    List<FeeRecord> list();

    //添加规费记录
    int add(FeeRecordPo feeRecordPo);

    //删除规费记录
    int delete(FeeRecordPo feeRecordPo);

    //修改规费记录
    int update(FeeRecordPo feeRecordPo);

    //根据id查询规费记录
    FeeRecordPo findbyid(FeeRecordPo feeRecordPo);

}
