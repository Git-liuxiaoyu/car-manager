package com.woniu.dao;

import com.woniu.po.AccidentRecordPo;
import com.woniu.po.CarPo;
import com.woniu.po.AccidentRecordPo;


import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:12
 */
public interface AccidentRecordDao {
    //列表
    List<AccidentRecordPo> list(String searchText);

    //添加
    int add(AccidentRecordPo accidentRecordPo);

    //删除
    int delete(Integer id);

    //修改
    int update(AccidentRecordPo accidentRecordPo);

    //根据id查询记录
//    AccidentRecordPo findbyid(AccidentRecordPo accidentRecordPo);

    int count(String searchText);

}
