package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.dao.DictionaryDao;
import com.woniu.domain.Dictionary;
import com.woniu.domain.Employee;
import com.woniu.po.DictionaryPo;
import com.woniu.po.MenuPo;
import com.woniu.service.DictionaryService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private DictionaryDao dictionaryDao;


    //查询分页字典
    @RequestMapping("list")
    public ResponseResult<PageInfo<Dictionary>> type(Integer p, String searchText, Integer size) {
        int pageIndex = 1;
        int pageSize = 5;
        if (p >= 1) {
            pageIndex = p;
        }
        if (pageSize >= 5) {
            pageSize = size;
        }
        Integer total = dictionaryService.count(searchText);

        List<Dictionary> dictionaryList = dictionaryService.list(searchText, pageIndex, pageSize);
//        List<DictionaryPo> dictionaryList = dictionaryDao.list(searchText);
        //测试
        PageInfo<Dictionary> pageInfo = new PageInfo<>(dictionaryList);
        return new ResponseResult<>(pageInfo);
    }

    //添加字典
    @RequestMapping("add")
    public ResponseResult addDictionary(Dictionary dictionary) {
        dictionaryService.add(dictionary);
        return ResponseResult.SUCCESS;
    }

    //删除字典
    @RequestMapping("delete")
    public ResponseResult delete(Integer id) {
        //先查询所有的列表看看此id的字段有没有儿子
        List<Dictionary> dictionary = dictionaryService.listGetByParentId(id);

        if (dictionary.size() != 0) {
            return new ResponseResult(201, "此菜单拥有子菜单无法删除");
        }
        dictionaryService.delete(id);
        return ResponseResult.SUCCESS;
    }

    //修改字典
    @RequestMapping("update")
    public ResponseResult update(Dictionary updateDictionary) {
        dictionaryService.update(updateDictionary);
        return ResponseResult.SUCCESS;
    }

    //查询所有的字典
    @RequestMapping("menu")
    public ResponseResult<List<DictionaryPo>> menu() {
        List<DictionaryPo> mens = dictionaryService.menuList();

        List<DictionaryPo> dictionaryPos = new ArrayList<>();//定义一个父列表的集合

        for (DictionaryPo d : mens) {
            if (d.getParentId() == 0) {
                dictionaryPos.add(d);//满足需求的存进父集合列表
            }
        }

        for (DictionaryPo d : dictionaryPos) {
            d.setChildren(getChildrenNode(d.getId(), mens));
        }
        return new ResponseResult<>(dictionaryPos);
    }

    //查询字典递归的方法
    public List<DictionaryPo> getChildrenNode(Integer id, List<DictionaryPo> perms) {
        List<DictionaryPo> childList = new ArrayList<>();//定义一个子列表的集合
        for (DictionaryPo p : perms) {
            if (p.getParentId() == id) {
                childList.add(p);
            }
        }
        for (DictionaryPo p : childList) {
            p.setChildren(getChildrenNode(p.getId(), perms));
        }
        if (perms.size() == 0) {
            return null;
        }
        return childList;
    }
}
