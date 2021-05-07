package com.woniu.service.impl;

import com.woniu.adapter.RepairRecordAdapter;
import com.woniu.domain.RepairRecord;
import com.woniu.service.RepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RepairRecordServiceImpl implements RepairRecordService {


    @Autowired
    private RepairRecordAdapter repairRecordAdapter;


    @Override
    public List<RepairRecord> list(String searchText,int pageIndex,int pageSize) {

        return repairRecordAdapter.list(searchText,pageIndex,pageSize);
    }

    @Override
    public void add(RepairRecord departureRecord) {
        repairRecordAdapter.add(departureRecord);

    }

    @Override
    public void update(RepairRecord departureRecord) {
        repairRecordAdapter.update(departureRecord);
    }
    @Override
    public int count(String searchText) {
        return repairRecordAdapter.count(searchText);
    }
    @Override
    public void delete(Integer id) {
         repairRecordAdapter.delete(id);
    }
}
