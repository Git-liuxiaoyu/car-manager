package com.woniu.dao;

import com.woniu.po.AccidentRecordPo;
import com.woniu.po.CarPo;


import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:12
 */
public interface AccidentRecordDao {
    public List<AccidentRecordPo> accidentRecordList(AccidentRecordPo accidentRecordPo);
    public int add(AccidentRecordPo accidentRecordPo);
    public int update(AccidentRecordPo accidentRecordPo);
    public int delete(AccidentRecordPo accidentRecordPo);
    public AccidentRecordPo findById(AccidentRecordPo accidentRecordPo);
}
