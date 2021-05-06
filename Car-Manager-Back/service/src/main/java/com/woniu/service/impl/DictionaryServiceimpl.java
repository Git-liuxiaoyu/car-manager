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
    public List<Dictionary> types(DictionaryPo dictionaryPo) {

        return dictionaryAdapter.types(dictionaryPo);
    }
}
