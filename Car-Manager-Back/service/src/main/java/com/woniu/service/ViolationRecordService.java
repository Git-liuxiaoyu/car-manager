package com.woniu.service;

import com.woniu.domain.ViolationRecord;
import com.woniu.po.ViolationRecordPo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ViolationRecordService {

    //违章记录列表
    List<ViolationRecord> list();

    //添加违章记录
    int add(ViolationRecordPo vrpo);

    //删除违章记录
    int delete(ViolationRecordPo vrpo);

    //修改违章记录
    int update(ViolationRecordPo vrpo);

    //根据id查询违章记录
    ViolationRecordPo findbyid(ViolationRecordPo vrpo);
}
