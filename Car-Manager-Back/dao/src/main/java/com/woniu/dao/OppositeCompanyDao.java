package com.woniu.dao;

import com.woniu.po.InsureRecordPo;
import com.woniu.po.OppositeCompanyPo;

import java.util.List;

/**
 * 往来单位dao
 */
public interface OppositeCompanyDao {



    //查询往来单位信息 按单位名称查询
    List<OppositeCompanyPo> list(String searchText);

    //总数
    int count(String searchText);

    /**
     * 添加
     * @param ocpo
     * @return
     */
    int add(OppositeCompanyPo ocpo);

    /**
     * 多条件修改
     * @return
     */
    int update(OppositeCompanyPo ocpo);

    /**
     * 删除
     * @param ocpo
     * @return
     */
    int delete(OppositeCompanyPo ocpo);

    /**
     * 根据id查询详情
     * @param ocpo
     * @return
     */
    OppositeCompanyPo findbyid(OppositeCompanyPo ocpo);


}
