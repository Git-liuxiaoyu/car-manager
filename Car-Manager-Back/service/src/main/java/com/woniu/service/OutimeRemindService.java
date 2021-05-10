package com.woniu.service;

import com.woniu.po.InsureRecordPo;
import com.woniu.po.OutimeRemindPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:38
 */

public interface OutimeRemindService {
//    public List<OutimeRemindPo> outimeRemindList();

    //保险记录列表
    public List<OutimeRemindPo> outimeRemindList(String nexttime,String thistime, int pageIndex, int pageSize);

    //求分页总数
    int count(String nexttime,String thistime);

    public int add(OutimeRemindPo outimeRemindPo);

    public int update(OutimeRemindPo outimeRemindPo);

    public int delete(OutimeRemindPo outimeRemindPo);

    public OutimeRemindPo findById(OutimeRemindPo outimeRemindPo);
}
