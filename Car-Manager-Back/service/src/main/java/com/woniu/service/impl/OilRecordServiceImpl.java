package com.woniu.service.impl;

import com.woniu.adapter.OilRecordAdapter;
import com.woniu.adapter.OilRecordAdapter;
import com.woniu.domain.OilRecord;
import com.woniu.po.CarPo;
import com.woniu.po.DriverPo;
import com.woniu.po.OilRecordPo;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.service.OilRecordService;
import com.woniu.service.OilRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class OilRecordServiceImpl implements OilRecordService {

    @Autowired
    private OilRecordAdapter oilRecordAdapter;


    @Override
    public List<OilRecord> oilRecordList(String searchText,int pageIndex,int pageSize) {

        return oilRecordAdapter.findLists(searchText,pageIndex,pageSize);
    }

    @Override
    public int add(OilRecord departureRecord) {
        return oilRecordAdapter.add(departureRecord);

    }

    @Override
    public int update(OilRecord departureRecord) {
        return oilRecordAdapter.update(departureRecord);
    }

    @Override
    public int delete(Integer id) {
        return oilRecordAdapter.delete(id);
    }


    //分页总数
    @Override
    public int count(String searchText) {

        return oilRecordAdapter.count(searchText);
    }


    //查询车牌号
    @Override
    public List<CarPo> carlist() {
        return oilRecordAdapter.carlist();
    }

    //查询往来单位下拉框
    @Override
    public List<OppositeCompanyPo> oppolist(Integer type) {
        return oilRecordAdapter.oppolist(type);
    }

    //加油人员下拉框
    @Override
    public List<DriverPo> driverlist() {
        return oilRecordAdapter.driverlist();
    }

    @Override
    public OilRecordPo findbyid(OilRecordPo orpo) {

        return oilRecordAdapter.findbyid(orpo);
    }


}
