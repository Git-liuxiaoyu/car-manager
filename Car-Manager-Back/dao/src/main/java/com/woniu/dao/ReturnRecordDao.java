package com.woniu.dao;

import com.woniu.po.ReturnRecordPo;

import java.util.List;

public interface ReturnRecordDao {
     //回车信息登记
    int add(ReturnRecordPo returnRecordPo);

    List<ReturnRecordPo> findList();
}
