package com.woniu.service;

import com.woniu.domain.OppositeCompany;
import com.woniu.po.OppositeCompanyPo;

import java.util.List;

public interface OppositeCompanyService {

    List<OppositeCompany> List();

    /**
     * 添加往来单位
     * @param ocpo
     * @return
     */
    int add(OppositeCompanyPo ocpo);

    //动态修改
    int update (OppositeCompanyPo ocpo);

    //删除
    int delete(OppositeCompanyPo ocpo);


    /**
     * 根据id查询详情
     * @param ocpo
     * @return
     */
    OppositeCompanyPo findbyid(OppositeCompanyPo ocpo);
}
