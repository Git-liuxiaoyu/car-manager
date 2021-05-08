package com.woniu.service;

import com.woniu.domain.AccidentRecord;
import com.woniu.po.AccidentRecordPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:38
 */

public interface AccidentRecordService {
    //查询维修信息
    List<AccidentRecord> list(String searchText, int pageIndex, int pageSize);

    //添加维修信息
    int add(AccidentRecord accidentRecord);
    //
    //修改维修信息
    int update(AccidentRecord accidentRecord);

    int count(String searchText);
    // 删除维修信息
    int delete(Integer id);
}
