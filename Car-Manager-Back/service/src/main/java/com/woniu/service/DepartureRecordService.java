package com.woniu.service;


import com.woniu.domain.DepartureRecord;
import com.woniu.domain.ReturnRecord;


import java.util.List;

public interface DepartureRecordService {

    void add(DepartureRecord departureRecord);

    void update(DepartureRecord departureRecord);

    int delete(Integer id);

    DepartureRecord getById(Integer id);

    Integer count(String searchText);

    List<DepartureRecord> findAll(String searchText, int pageIndex, int pageSize);

}
