package com.woniu.controller;

import com.woniu.po.MenuPo;
import com.woniu.service.MenuService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    //查询所有的权限
    @RequestMapping("/list")
    public ResponseResult<List<MenuPo>> menuList() {
        List<MenuPo> mens = menuService.list();

        List<MenuPo> menuPoList = new ArrayList<>();//定义一个父列表的集合

        for (MenuPo d : mens) {
            if (d.getParentId() == null) {
                menuPoList.add(d);//满足需求的存进父集合列表
            }
        }
        for (MenuPo d : menuPoList) {
            d.setChildren(getChildrenNode(d.getId(), mens));
        }
        return new ResponseResult<>(menuPoList);
    }

    public List<MenuPo> getChildrenNode(Integer id, List<MenuPo> perms) {
        List<MenuPo> childList = new ArrayList<>();//定义一个子列表的集合
        for (MenuPo p : perms) {
            if (p.getParentId() == id) {
                childList.add(p);
            }
        }
        for (MenuPo p : childList) {
            p.setChildren(getChildrenNode(p.getId(), perms));
        }
        if (perms.size() == 0) {
            return null;
        }
        return childList;
    }
}
