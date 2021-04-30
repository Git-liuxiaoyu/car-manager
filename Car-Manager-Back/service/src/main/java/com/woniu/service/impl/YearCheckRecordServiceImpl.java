package com.woniu.service.impl;

import com.woniu.adapter.YearCheckRecordAdapter;
import com.woniu.po.YearCheckRecordPo;
import com.woniu.service.YearCheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:37
 */
@Component
public class YearCheckRecordServiceImpl implements YearCheckRecordService {
    @Autowired
    private YearCheckRecordAdapter yearCheckRecordAdapter;
    @Override
    public List<YearCheckRecordPo> yearCheckRecordList() {
        return yearCheckRecordAdapter.findYearCheckRecordList();
    }

    @Override
    public int add(YearCheckRecordPo yearCheckRecordPo) {
        return yearCheckRecordAdapter.add(yearCheckRecordPo);
    }

    @Override
    public int update(YearCheckRecordPo yearCheckRecordPo) {
        return yearCheckRecordAdapter.update(yearCheckRecordPo);
    }

    @Override
    public int delete(YearCheckRecordPo yearCheckRecordPo) {
        return yearCheckRecordAdapter.delete(yearCheckRecordPo);
    }

    @Override
    public YearCheckRecordPo findById(YearCheckRecordPo yearCheckRecordPo) {
        return yearCheckRecordAdapter.findById(yearCheckRecordPo);
    }
}
