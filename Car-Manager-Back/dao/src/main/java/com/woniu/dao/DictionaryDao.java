package com.woniu.dao;

import com.woniu.po.DictionaryPo;

import java.util.Dictionary;
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
    Integer count(String searchText);

    List<DictionaryPo> list(String searchText);

    List<DictionaryPo> menuList();

    void add(DictionaryPo dictionaryPo);

    List<DictionaryPo> listGetByParentId(Integer id);

    void delete(Integer id);

    void update(DictionaryPo dictionaryPo);
}
