package com.woniu.service.impl;

import com.woniu.adapter.DictionaryAdapter;
import com.woniu.domain.Dictionary;
import com.woniu.po.DictionaryPo;
import com.woniu.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典
 */
@Service
@Transactional
public class DictionaryServiceimpl implements DictionaryService {

    @Autowired
    private DictionaryAdapter dictionaryAdapter;

    @Override
    public Integer count(String searchText) {
        return dictionaryAdapter.count(searchText);
    }

    @Override
    public List<Dictionary> list(String searchText, int pageIndex, int pageSize) {
        return dictionaryAdapter.findDictionaryLists(searchText,pageIndex,pageSize);
    }

    @Override
    public List<DictionaryPo> menuList() {
        return dictionaryAdapter.menuList();
    }

    @Override
    public void add(Dictionary dictionary) {
        dictionaryAdapter.add(dictionary);
    }

    @Override
    public List<Dictionary> listGetByParentId(Integer id) {
        return dictionaryAdapter.listGetByParentId(id);
    }

    @Override
    public void delete(Integer id) {
        dictionaryAdapter.dalete(id);
    }

    @Override
    public void update(Dictionary dictionary) {
        dictionaryAdapter.update(dictionary);
    }

}
