package com.woniu.dao;

import com.woniu.po.InsureRecordPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:12
 */

public interface InsureRecordDao {
    public List<InsureRecordPo> insureRecordList();
    public int add(InsureRecordPo insureRecordPo);
    public int update(InsureRecordPo insureRecordPo);
    public int delete(InsureRecordPo insureRecordPo);
    public InsureRecordPo findById(InsureRecordPo insureRecordPo);
}
