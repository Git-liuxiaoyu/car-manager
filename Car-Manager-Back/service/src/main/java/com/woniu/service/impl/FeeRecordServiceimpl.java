package com.woniu.service.impl;

import com.woniu.adapter.FeeRecordAdapter;
import com.woniu.domain.FeeRecord;
import com.woniu.domain.GetRecord;
import com.woniu.po.FeeRecordPo;
import com.woniu.po.GetRecordPo;
import com.woniu.service.FeeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FeeRecordServiceimpl implements FeeRecordService {

    @Autowired
    private FeeRecordAdapter feeRecordAdapter;


    @Override
    public List<FeeRecord> list() {

        return feeRecordAdapter.Lists();
    }

    @Override
    public int add(FeeRecordPo feeRecordPo) {

        return feeRecordAdapter.add(feeRecordPo);
    }

    @Override
    public int delete(FeeRecordPo feeRecordPo) {
        return feeRecordAdapter.delete(feeRecordPo);
    }

    @Override
    public int update(FeeRecordPo feeRecordPo) {
        return feeRecordAdapter.update(feeRecordPo);
    }

    @Override
    public FeeRecordPo findbyid(FeeRecordPo feeRecordPo) {
        return feeRecordAdapter.findbyid(feeRecordPo);
    }
}
