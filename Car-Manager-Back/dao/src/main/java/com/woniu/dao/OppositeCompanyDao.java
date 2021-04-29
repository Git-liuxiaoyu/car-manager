package com.woniu.dao;

import com.woniu.po.OppositeCompanyPo;

import java.util.List;

/**
 * 往来单位dao
 */
public interface OppositeCompanyDao {

    /**
     * 查询集合
     * @return
     */
    List<OppositeCompanyPo> list();

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



}
