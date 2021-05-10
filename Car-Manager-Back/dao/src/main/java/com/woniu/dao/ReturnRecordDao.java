package com.woniu.dao;

import com.woniu.po.ReturnRecordPo;

import java.util.List;

public interface ReturnRecordDao {
     //回车信息登记
    void add(ReturnRecordPo returnRecordPo);

    ReturnRecordPo getByDictionaryId(Integer id);
}
