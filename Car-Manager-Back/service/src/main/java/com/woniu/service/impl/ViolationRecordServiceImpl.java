package com.woniu.service.impl;

import com.woniu.adapter.ViolationRecordAdapter;
import com.woniu.domain.ViolationRecord;
import com.woniu.service.ViolationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ViolationRecordServiceImpl implements ViolationRecordService {


    @Autowired
    private ViolationRecordAdapter violationRecordAdapter;


    @Override
    public List<ViolationRecord> list(String searchText,int pageIndex,int pageSize) {

        return violationRecordAdapter.list(searchText,pageIndex,pageSize);
    }

    @Override
    public void add(ViolationRecord violationRecord) {
        violationRecordAdapter.add(violationRecord);

    }

    @Override
    public void update(ViolationRecord violationRecord) {
        violationRecordAdapter.update(violationRecord);
    }
    @Override
    public int count(String searchText) {
        return violationRecordAdapter.count(searchText);
    }
    @Override
    public void delete(Integer id) {
         violationRecordAdapter.delete(id);
    }
}
