package com.woniu.service.impl;

import com.woniu.adapter.DepartureRecordAdapter;
import com.woniu.adapter.DriverAdapter;
import com.woniu.domain.DepartureRecord;
import com.woniu.domain.Driver;
import com.woniu.service.DepartureRecordService;
import com.woniu.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class DepartureRecordServiceImpl implements DepartureRecordService {


    @Autowired
    private DepartureRecordAdapter departureRecordAdapter;


    @Override
    public List<DepartureRecord> departureRecordList() {

        return departureRecordAdapter.findDriverLists();
    }

    @Override
    public int add(DepartureRecord departureRecord) {
        return departureRecordAdapter.add(departureRecord);
//     return 1;
    }

    @Override
    public int update(DepartureRecord departureRecord) {
        return departureRecordAdapter.update(departureRecord);
    }

    @Override
    public int delete(Integer id) {
        return departureRecordAdapter.delete(id);
    }

    @Override
    public DepartureRecord getById(Integer id) {
        return departureRecordAdapter.getById(id);
    }
}
