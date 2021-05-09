package com.woniu.dao;

import com.woniu.po.InsureRecordPo;
import com.woniu.po.KeepRecordPo;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/4/29 18:12
 */

public interface InsureRecordDao {

    //public List<InsureRecordPo> insureRecordList();

    //查询保险信息信息
    List<InsureRecordPo> insureRecordList(String searchText);

    //总数
    int count(String searchText);



    public int add(InsureRecordPo insureRecordPo);


    public int update(InsureRecordPo insureRecordPo);

    public int delete(InsureRecordPo insureRecordPo);


    public InsureRecordPo findById(InsureRecordPo insureRecordPo);

}
