package com.woniu.service.impl;

import com.woniu.adapter.YearCheckRecordAdapter;
import com.woniu.domain.YearCheckRecord;
import com.woniu.po.YearCheckRecordPo;
import com.woniu.service.YearCheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class YearCheckRecordServiceImpl implements YearCheckRecordService {


    @Autowired
    private YearCheckRecordAdapter yearCheckRecordAdapter;


    @Override
    public List<YearCheckRecord> list(String searchText,int pageIndex,int pageSize) {

        return yearCheckRecordAdapter.list(searchText,pageIndex,pageSize);
    }

    @Override
    public void add(YearCheckRecord yearCheckRecord) {
        yearCheckRecordAdapter.add(yearCheckRecord);

    }

    @Override
    public void update(YearCheckRecord yearCheckRecord) {
        yearCheckRecordAdapter.update(yearCheckRecord);
    }
    @Override
    public int count(String searchText) {
        return yearCheckRecordAdapter.count(searchText);
    }
    @Override
    public void delete(Integer id) {
        yearCheckRecordAdapter.delete(id);
    }

    @Override
    public YearCheckRecordPo findById(YearCheckRecord yearCheckRecord) {
        return yearCheckRecordAdapter.findbyid(yearCheckRecord);
    }
}
