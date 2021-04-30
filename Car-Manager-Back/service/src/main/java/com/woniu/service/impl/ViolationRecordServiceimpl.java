package com.woniu.service.impl;

import com.woniu.adapter.ViolationAdapter;
import com.woniu.domain.ViolationRecord;
import com.woniu.po.ViolationRecordPo;
import com.woniu.service.ViolationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ViolationRecordServiceimpl implements ViolationRecordService {

    @Autowired
    private ViolationAdapter violationAdapter;

    @Override
    public List<ViolationRecord> list() {

        return violationAdapter.Lists();
    }

    @Override
    public int add(ViolationRecordPo vrpo) {
        return violationAdapter.add(vrpo);
    }

    @Override
    public int delete(ViolationRecordPo vrpo) {
        return violationAdapter.delete(vrpo);
    }

    @Override
    public int update(ViolationRecordPo vrpo) {
        return violationAdapter.update(vrpo);
    }

    @Override
    public ViolationRecordPo findbyid(ViolationRecordPo vrpo) {
        return violationAdapter.findbyid(vrpo);
    }
}
