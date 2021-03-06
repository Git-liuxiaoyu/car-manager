package com.woniu.service.impl;

import com.woniu.adapter.OppositeCompanyAdapter;
import com.woniu.domain.OppositeCompany;
import com.woniu.po.InsureRecordPo;
import com.woniu.po.OppositeCompanyPo;
import com.woniu.service.OppositeCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class OppositeCompanyServiceimpl implements OppositeCompanyService {


    @Autowired
    private OppositeCompanyAdapter oppositeCompanyAdapter;



    @Override
    public List<OppositeCompany> List(String searchText, int pageIndex, int pageSize) {
        return oppositeCompanyAdapter.findoppositeCompanyLists(searchText,pageIndex,pageSize);
    }

    //总数
    @Override
    public int count(String searchText) {
        return oppositeCompanyAdapter.count(searchText);
    }

    /**
     * 添加
     * @param ocpo
     * @return
     */
    @Override
    public int add(OppositeCompanyPo ocpo) {
        return oppositeCompanyAdapter.add(ocpo);
    }

    /**
     * 修改
     * @param ocpo
     * @return
     */
    @Override
    public int update(OppositeCompanyPo ocpo) {
        return oppositeCompanyAdapter.update(ocpo);
    }


    /**
     * 删除
     * @param ocpo
     * @return
     */
    @Override
    public int delete(OppositeCompanyPo ocpo) {
        return oppositeCompanyAdapter.delete(ocpo);
    }

    @Override
    public OppositeCompanyPo findbyid(OppositeCompanyPo ocpo) {
        return oppositeCompanyAdapter.findbyid(ocpo);
    }
}
