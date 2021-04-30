package com.woniu.service.impl;

import com.woniu.adapter.GetRecordAdapter;
import com.woniu.domain.GetRecord;
import com.woniu.po.GetRecordPo;
import com.woniu.service.GetRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GetRecordServiceimpl implements GetRecordService {

    @Autowired
    private GetRecordAdapter getRecordAdapter;

    @Override
    public List<GetRecord> list() {

        return getRecordAdapter.Lists();
    }

    @Override
    public int add(GetRecordPo getRecordPo) {

        return getRecordAdapter.add(getRecordPo);
    }

    @Override
    public int delete(GetRecordPo getRecordPo) {
        return getRecordAdapter.delete(getRecordPo);
    }

    @Override
    public int update(GetRecordPo getRecordPo) {
        return getRecordAdapter.update(getRecordPo);
    }

    @Override
    public GetRecordPo findbyid(GetRecordPo getRecordPo) {
        return getRecordAdapter.findbyid(getRecordPo);
    }
}
