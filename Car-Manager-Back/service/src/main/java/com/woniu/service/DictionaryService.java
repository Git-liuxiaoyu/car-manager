package com.woniu.service;

import com.woniu.domain.Dictionary;
import com.woniu.po.DictionaryPo;

import java.util.List;

public interface DictionaryService {

    /**
     * 根据parentid查询类别
     * @param
     * @return
     */

    Integer count(String searchText);

    List<Dictionary> findAll(String searchText, int pageIndex, int pageSize);

    List<DictionaryPo> list();

    void add(Dictionary dictionary);

    List<Dictionary> listGetByParentId(Integer id);

    void delete(Integer id);

    void update(Dictionary dictionary);
}
