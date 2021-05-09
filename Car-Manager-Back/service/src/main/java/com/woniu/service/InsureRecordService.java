package com.woniu.service;

import com.woniu.domain.KeepRecord;
import com.woniu.po.InsureRecordPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:38
 */

public interface InsureRecordService {

    //保险记录列表
    public List<InsureRecordPo> insureRecordList(String searchText, int pageIndex, int pageSize);

    //求分页总数
    int count(String searchText);

    public int add(InsureRecordPo insureRecordPo);
    public int update(InsureRecordPo insureRecordPo);
    public int delete(InsureRecordPo insureRecordPo);
    public InsureRecordPo findById(InsureRecordPo insureRecordPo);
}
