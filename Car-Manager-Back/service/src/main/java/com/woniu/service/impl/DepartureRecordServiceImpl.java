package com.woniu.service.impl;

import com.woniu.adapter.DepartureRecordAdapter;
import com.woniu.adapter.DriverAdapter;
import com.woniu.domain.DepartureRecord;
import com.woniu.domain.Driver;
import com.woniu.domain.ReturnRecord;
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
    public void add(DepartureRecord departureRecord) {
        departureRecordAdapter.add(departureRecord);
    }

    @Override
    public void update(DepartureRecord departureRecord) {
        departureRecordAdapter.update(departureRecord);
    }

    @Override
    public int delete(Integer id) {
        return departureRecordAdapter.delete(id);
    }

    @Override
    public DepartureRecord getById(Integer id) {
        return departureRecordAdapter.getById(id);
    }

    @Override
    public Integer count(String searchText) {
        return departureRecordAdapter.count(searchText);
    }

    @Override
    public List<DepartureRecord> findAll(String searchText, int pageIndex, int pageSize) {
        return departureRecordAdapter.findAll(searchText,pageIndex,pageSize);
    }
}
