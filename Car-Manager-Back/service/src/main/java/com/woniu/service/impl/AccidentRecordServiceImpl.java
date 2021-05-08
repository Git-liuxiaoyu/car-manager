package com.woniu.service.impl;

import com.woniu.adapter.AccidentRecordAdapter;
//import com.woniu.po.AccidentRecord;
import com.woniu.domain.AccidentRecord;
import com.woniu.domain.AccidentRecord;
import com.woniu.po.AccidentRecordPo;
import com.woniu.service.AccidentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:37
 */
@Component
public class AccidentRecordServiceImpl implements AccidentRecordService {
    @Autowired
    private AccidentRecordAdapter accidentRecordAdapter;
    @Override
    public List<AccidentRecord> list(String searchText, int pageIndex, int pageSize) {

        return accidentRecordAdapter.List(searchText,pageIndex,pageSize);
    }

    @Override
    public int add(AccidentRecord accidentRecord) {

        return accidentRecordAdapter.add(accidentRecord);
    }

    @Override
    public int delete(Integer id) {
        return accidentRecordAdapter.delete(id);
    }

    @Override
    public int update(AccidentRecord feeRecord) {
        return accidentRecordAdapter.update(feeRecord);
    }



    @Override
    public int count(String searchText) {
        return accidentRecordAdapter.count(searchText);
    }


}
