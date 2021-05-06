package com.woniu.controller;


import com.woniu.domain.Dictionary;
import com.woniu.po.DictionaryPo;
import com.woniu.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/dictionary/")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping("type")
    public List<Dictionary> type(int parentid){
        DictionaryPo dipo = new DictionaryPo();
        dipo.setParentId(parentid);
        List<Dictionary> types = dictionaryService.types(dipo);
        return types;
    }

}
