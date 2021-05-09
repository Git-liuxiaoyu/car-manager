package com.woniu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.DepartureRecordDao;
import com.woniu.domain.DepartureRecord;
import com.woniu.domain.Driver;
import com.woniu.po.DepartureRecordPo;
import com.woniu.po.DriverPo;
import com.woniu.redis.DepartureRecordRedisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartureRecordAdapter {
    @Autowired
    private DepartureRecordDao departureRecordDao;

    @Autowired
    private DepartureRecordRedisDao departureRecordRedisDao;

    public List<DepartureRecord> findAll(String searchText, int pageIndex, int pageSize) {
        List<DepartureRecordPo> departureRecordPoList = departureRecordRedisDao.list(searchText, pageIndex, pageSize);
        ObjectMapper objectMapper = new ObjectMapper();
        if (departureRecordPoList.size() == 0) {
            departureRecordPoList = departureRecordDao.findList(searchText);
            departureRecordRedisDao.addRedisDriverList(departureRecordPoList, searchText, pageIndex, pageSize);
        }
        List<DepartureRecord> departureRecords = new ArrayList<>();
        for (DepartureRecordPo departureRecordPo : departureRecordPoList) {
            DepartureRecord item = new DepartureRecord();
            BeanUtils.copyProperties(departureRecordPo, item);
            departureRecords.add(item);
        }
        return departureRecords;
    }


    /**
     * 添加驾驶员档案
     *
     * @param departureRecord
     * @return
     */
    public int add(DepartureRecord departureRecord) {

        DepartureRecordPo item = new DepartureRecordPo();
        BeanUtils.copyProperties(departureRecord, item);
        int i = departureRecordDao.add(item);
        return i;
    }

    /**
     * 修改出车信息
     *
     * @param departureRecord
     * @return
     */
    public int update(DepartureRecord departureRecord) {

        DepartureRecordPo item = new DepartureRecordPo();
        BeanUtils.copyProperties(departureRecord, item);
        int i = departureRecordDao.update(item);
        if (i > 0) {
            List<DepartureRecordPo> departureRecordPos = departureRecordDao.findList();
        }
        return i;
    }

    /**
     * 修改出车信息
     *
     * @param id
     * @return
     */
    public int delete(Integer id) {

        int i = departureRecordDao.delete(id);
        if (i > 0) {
            List<DepartureRecordPo> departureRecordPos = departureRecordDao.findList();
        }
        return i;
    }


    public DepartureRecord getById(Integer id) {
        DepartureRecordPo departureRecordPo = departureRecordDao.getById(id);
        DepartureRecord item = new DepartureRecord();
        BeanUtils.copyProperties(departureRecordPo, item);
        return item;

    }


    public Integer count(String searchText) {
        return departureRecordDao.count(searchText);
    }

}
