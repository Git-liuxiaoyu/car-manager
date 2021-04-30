package com.woniu.service;

import com.woniu.po.AccidentRecordPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:38
 */

public interface AccidentRecordService {
    public List<AccidentRecordPo> accidentRecordList(AccidentRecordPo accidentRecordPo);
    public int add(AccidentRecordPo accidentRecordPo);
    public int update(AccidentRecordPo accidentRecordPo);
    public int delete(AccidentRecordPo accidentRecordPo);
    public AccidentRecordPo findById(AccidentRecordPo accidentRecordPo);
}
