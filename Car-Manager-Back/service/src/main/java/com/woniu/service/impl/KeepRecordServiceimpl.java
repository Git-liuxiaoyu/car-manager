package com.woniu.service.impl;

import com.woniu.adapter.KeepRecordAdapter;
import com.woniu.domain.KeepRecord;
import com.woniu.po.KeepRecordPo;
import com.woniu.service.KeepRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class KeepRecordServiceimpl implements KeepRecordService {

    @Autowired
    private KeepRecordAdapter keeRecordAdapter;

    @Override
    public List<KeepRecord> list() {
        return keeRecordAdapter.Lists();
    }

    @Override
    public int add(KeepRecordPo krpo) {
        return keeRecordAdapter.add(krpo);
    }

    @Override
    public int delete(KeepRecordPo krpo) {
        return keeRecordAdapter.delete(krpo);
    }

    @Override
    public int update(KeepRecordPo krpo) {
        return keeRecordAdapter.update(krpo);
    }

    @Override
    public KeepRecordPo findbyid(KeepRecordPo krpo) {
        return keeRecordAdapter.findbyid(krpo);
    }
}
