package com.woniu.service.impl;

import com.woniu.adapter.InsureRecordAdapter;
import com.woniu.po.InsureRecordPo;
import com.woniu.service.InsureRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:37
 */
@Component
public class InsureRecordServiceImpl implements InsureRecordService {
    @Autowired
    private InsureRecordAdapter insureRecordAdapter;
    @Override
    public List<InsureRecordPo> insureRecordList() {
        return insureRecordAdapter.findInsureRecordList();
    }

    @Override
    public int add(InsureRecordPo insureRecordPo) {
        return insureRecordAdapter.add(insureRecordPo);
    }

    @Override
    public int update(InsureRecordPo insureRecordPo) {
        return insureRecordAdapter.update(insureRecordPo);
    }

    @Override
    public int delete(InsureRecordPo insureRecordPo) {
        return insureRecordAdapter.delete(insureRecordPo);
    }

    @Override
    public InsureRecordPo findById(InsureRecordPo insureRecordPo) {
        return insureRecordAdapter.findById(insureRecordPo);
    }
}
