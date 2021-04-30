package com.woniu.service;


import com.woniu.domain.DepartureRecord;


import java.util.List;

public interface DepartureRecordService {

    public List<DepartureRecord> departureRecordList();

    int add(DepartureRecord departureRecord);

    int update(DepartureRecord departureRecord);

    int delete(Integer id);

    DepartureRecord getById(Integer id);
}
