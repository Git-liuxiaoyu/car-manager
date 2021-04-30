package com.woniu.service.impl;

import com.woniu.adapter.AccidentRecordAdapter;
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
    public List<AccidentRecordPo> accidentRecordList(AccidentRecordPo accidentRecordPo) {
        return accidentRecordAdapter.findAccidentRecordList(accidentRecordPo);
    }

    @Override
    public int add(AccidentRecordPo accidentRecordPo) {
        return accidentRecordAdapter.add(accidentRecordPo);
    }

    @Override
    public int update(AccidentRecordPo accidentRecordPo) {
        return accidentRecordAdapter.update(accidentRecordPo);
    }

    @Override
    public int delete(AccidentRecordPo accidentRecordPo) {
        return accidentRecordAdapter.delete(accidentRecordPo);
    }

    @Override
    public AccidentRecordPo findById(AccidentRecordPo accidentRecordPo) {
        return accidentRecordAdapter.findById(accidentRecordPo);
    }
}
