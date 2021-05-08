package com.woniu.service;

import com.woniu.domain.KeepRecord;
import com.woniu.domain.OilRecord;
import com.woniu.po.KeepRecordPo;

import java.util.List;

public interface KeepRecordService {


    //规费记录列表
    public List<KeepRecord> keepList(String searchText, int pageIndex, int pageSize);

    //添加规费记录
    int add(KeepRecordPo krpo);

    //删除规费记录
    int delete(KeepRecordPo krpo);

    //修改规费记录
    int update(KeepRecordPo krpo);

    //根据id查询规费记录
    KeepRecordPo findbyid(KeepRecordPo krpo);

    //求分页总数
    int count(String searchText);
}
