package com.woniu.service;

import com.woniu.po.OutimeRemindPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:38
 */

public interface OutimeRemindService {
    public List<OutimeRemindPo> outimeRemindList();
    public int add(OutimeRemindPo outimeRemindPo);
    public int update(OutimeRemindPo outimeRemindPo);
    public int delete(OutimeRemindPo outimeRemindPo);
    public OutimeRemindPo findById(OutimeRemindPo outimeRemindPo);
}
