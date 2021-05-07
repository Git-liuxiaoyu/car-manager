package com.woniu.service;


import com.woniu.domain.OilRecord;
import com.woniu.domain.ReturnRecord;
import com.woniu.po.CarPo;
import com.woniu.po.DriverPo;
import com.woniu.po.OilRecordPo;
import com.woniu.po.OppositeCompanyPo;

import java.util.List;

public interface OilRecordService {

    public List<OilRecord> oilRecordList(String searchText,int pageIndex,int pageSize);

    int add(OilRecord oilRecord);

    int update(OilRecord oilRecord);

    int delete(Integer id);

    //求分页总数
    int count(String searchText);

    //查询车牌号
    List<CarPo> carlist();

    //查询往来单位下拉框
    List<OppositeCompanyPo> oppolist(Integer type);

    //加油人员下拉框
    List<DriverPo> driverlist();

    //根据id查询
    OilRecordPo findbyid(OilRecordPo orpo);

}
