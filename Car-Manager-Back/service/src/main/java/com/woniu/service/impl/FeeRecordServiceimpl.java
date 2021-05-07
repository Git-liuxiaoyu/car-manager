package com.woniu.service.impl;

import com.woniu.adapter.FeeRecordAdapter;
import com.woniu.domain.FeeRecord;
import com.woniu.domain.GetRecord;
import com.woniu.po.CarPo;
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
    public List<FeeRecord> list(String searchText,int pageIndex,int pageSize) {

        return feeRecordAdapter.Lists(searchText,pageIndex,pageSize);
    }

    @Override
    public int add(FeeRecordPo feeRecordPo) {

        return feeRecordAdapter.add(feeRecordPo);
    }

    @Override
    public int delete(FeeRecord feeRecord) {
        return feeRecordAdapter.delete(feeRecord);
    }

    @Override
    public int update(FeeRecord feeRecord) {
        return feeRecordAdapter.update(feeRecord);
    }

    @Override
    public FeeRecordPo findbyid(FeeRecordPo feeRecordPo) {
        return feeRecordAdapter.findbyid(feeRecordPo);
    }

    @Override
    public int count(String searchText) {
        return feeRecordAdapter.count(searchText);
    }

    @Override
    public List<CarPo> getAllCarNum() {
        return feeRecordAdapter.getAllCarNum();
    }


}
