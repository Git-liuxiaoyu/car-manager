package com.woniu.service;

import com.woniu.domain.Dictionary;
import com.woniu.po.DictionaryPo;

import java.util.List;

public interface DictionaryService {

    /**
     * 根据parentid查询类别
     * @param dictionaryPo
     * @return
     */
    List<Dictionary> types(DictionaryPo dictionaryPo);
}
