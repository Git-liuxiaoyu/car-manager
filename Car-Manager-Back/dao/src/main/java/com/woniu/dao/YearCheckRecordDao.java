package com.woniu.dao;

import com.woniu.po.YearCheckRecordPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:12
 */

public interface YearCheckRecordDao {
    public List<YearCheckRecordPo> yearCheckRecordList();
    public int add(YearCheckRecordPo yearCheckRecordPo);
    public int update(YearCheckRecordPo yearCheckRecordPo);
    public int delete(YearCheckRecordPo yearCheckRecordPo);
    public YearCheckRecordPo findById(YearCheckRecordPo yearCheckRecordPo);
}
