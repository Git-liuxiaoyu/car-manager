package com.woniu.service.impl;

import com.woniu.adapter.DepartureRecordAdapter;
import com.woniu.adapter.ReturnRecordAdapter;
import com.woniu.domain.DepartureRecord;
import com.woniu.domain.ReturnRecord;
import com.woniu.service.DepartureRecordService;
import com.woniu.service.ReturnRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ReturnRecordServiceImpl implements ReturnRecordService {


    @Autowired
    private ReturnRecordAdapter returnRecordAdapter;


//    @Override
//    public List<DepartureRecord> departureRecordList() {
//
//        return departureRecordAdapter.findDriverLists();
//    }

    @Override
    public int add(ReturnRecord returnRecord) {
        return returnRecordAdapter.add(returnRecord);

    }

//    @Override
//    public int update(DepartureRecord departureRecord) {
//        return departureRecordAdapter.update(departureRecord);
//    }
//
//    @Override
//    public int delete(Integer id) {
//        return departureRecordAdapter.delete(id);
//    }
}
