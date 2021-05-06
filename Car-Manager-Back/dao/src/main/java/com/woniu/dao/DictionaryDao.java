package com.woniu.dao;

import com.woniu.po.DictionaryPo;

import java.util.List;

/**
 * 字典dao
 */
public interface DictionaryDao {
    /**
     * 根据prrentid查询类型
     * @param
     * @return
     */
    List<DictionaryPo> types(DictionaryPo dictionaryPo);

}
