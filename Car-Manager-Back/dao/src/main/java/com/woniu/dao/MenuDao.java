package com.woniu.dao;

import com.woniu.po.MenuPo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/05/08/16:34
 * @Description: 菜单权限表
 */
public interface MenuDao {
    List<MenuPo> menuPoList();
}
