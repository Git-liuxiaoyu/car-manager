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
    public List<RepairRecord> findList() {

        return repairRecordAdapter.findLists();
    }

    @Override
    public int add(RepairRecord departureRecord) {
        return repairRecordAdapter.add(departureRecord);
//     return 1;
    }

    @Override
    public int update(RepairRecord departureRecord) {
        return repairRecordAdapter.update(departureRecord);
    }

    @Override
    public int delete(Integer id) {
        return repairRecordAdapter.delete(id);
    }
}
