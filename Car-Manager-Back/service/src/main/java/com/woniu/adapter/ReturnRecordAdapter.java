package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.DepartureRecordDao;
import com.woniu.dao.ReturnRecordDao;
import com.woniu.domain.DepartureRecord;
import com.woniu.domain.ReturnRecord;
import com.woniu.po.DepartureRecordPo;
import com.woniu.po.ReturnRecordPo;
import com.woniu.redis.DepartureRecordRedisDao;
import com.woniu.redis.ReturnRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReturnRecordAdapter {
    @Autowired
    private ReturnRecordDao returnRecordDao;

    @Autowired
    private ReturnRecordRedisDao returnRecordRedisDao;

    public void add(ReturnRecord returnRecord){
        ReturnRecordPo item = new ReturnRecordPo();
        BeanUtils.copyProperties(returnRecord, item);
        returnRecordDao.add(item);
    }

    public ReturnRecord getByDictionaryId(Integer id) {
        ReturnRecordPo returnRecordPo=returnRecordDao.getByDictionaryId(id);
        ReturnRecord returnRecord=new ReturnRecord();
        BeanUtils.copyProperties(returnRecordPo, returnRecord);
        return returnRecord;
    }
}
