package com.woniu.service;


import com.woniu.domain.DepartureRecord;


import java.util.List;

public interface DepartureRecordService {

    int add(DepartureRecord departureRecord);

    int update(DepartureRecord departureRecord);

    int delete(Integer id);

    DepartureRecord getById(Integer id);

    Integer count(String searchText);

    List<DepartureRecord> findAll(String searchText, int pageIndex, int pageSize);
}
