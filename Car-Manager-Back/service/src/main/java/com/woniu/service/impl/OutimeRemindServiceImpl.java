package com.woniu.service.impl;

import com.woniu.adapter.OutimeRemindAdapter;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.OutimeRemindPo;
import com.woniu.service.OutimeRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:37
 */
@Component
public class OutimeRemindServiceImpl implements OutimeRemindService {
    @Autowired
    private OutimeRemindAdapter outimeRemindAdapter;


    @Override
    public List<OutimeRemindPo> outimeRemindList(String nexttime,String thistime, int pageIndex, int pageSize) {
        return outimeRemindAdapter.findOutimeRemindList(nexttime,thistime,pageIndex,pageSize);
    }

    //总数
    @Override
    public int count(String nexttime,String thistime) {
        return outimeRemindAdapter.count(nexttime,thistime);
    }


    @Override
    public int add(OutimeRemindPo outimeRemindPo) {
        return outimeRemindAdapter.add(outimeRemindPo);
    }

    @Override
    public int update(OutimeRemindPo outimeRemindPo) {
        return outimeRemindAdapter.update(outimeRemindPo);
    }

    @Override
    public int delete(OutimeRemindPo outimeRemindPo) {
        return outimeRemindAdapter.delete(outimeRemindPo);
    }

    @Override
    public OutimeRemindPo findById(OutimeRemindPo outimeRemindPo) {
        return outimeRemindAdapter.findById(outimeRemindPo);
    }
}
