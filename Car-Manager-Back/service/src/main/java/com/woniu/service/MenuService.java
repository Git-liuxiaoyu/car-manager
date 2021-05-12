package com.woniu.service;


import com.woniu.po.MenuPo;

import java.util.List;
import java.util.Map;

public interface MenuService {

    List<MenuPo> list();
    public void deletePermsByRoleId(Integer id);
    public void addRight(Map<String,Integer> map);
    public List<Integer> getPermsByRoleId(Integer roleId);
}
