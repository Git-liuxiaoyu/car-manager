package com.woniu.service.impl;

import com.woniu.adapter.OilRecordAdapter;
import com.woniu.adapter.OilRecordAdapter;
import com.woniu.domain.OilRecord;
import com.woniu.service.OilRecordService;
import com.woniu.service.OilRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class OilRecordServiceImpl implements OilRecordService {


    @Autowired
    private OilRecordAdapter oilRecordAdapter;


    @Override
    public List<OilRecord> oilRecordList() {

        return oilRecordAdapter.findLists();
    }

    @Override
    public int add(OilRecord departureRecord) {
        return oilRecordAdapter.add(departureRecord);
//     return 1;
    }

    @Override
    public int update(OilRecord departureRecord) {
        return oilRecordAdapter.update(departureRecord);
    }

    @Override
    public int delete(Integer id) {
        return oilRecordAdapter.delete(id);
    }
}
