package com.woniu.dao;

import com.woniu.po.OutimeRemindPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:12
 */

public interface OutimeRemindDao {
    public List<OutimeRemindPo> outimeRemindList();
    public int add(OutimeRemindPo outimeRemindPo);
    public int update(OutimeRemindPo outimeRemindPo);
    public int delete(OutimeRemindPo outimeRemindPo);
    public OutimeRemindPo findById(OutimeRemindPo outimeRemindPo);
}
