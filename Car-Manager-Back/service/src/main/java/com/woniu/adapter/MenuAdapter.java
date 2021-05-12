package com.woniu.adapter;

import com.woniu.dao.MenuDao;
import com.woniu.po.MenuPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MenuAdapter {

    @Autowired
    private MenuDao menuDao;

    public List<MenuPo> list() {
        List<MenuPo> menuPos = menuDao.menuPoList();
        return menuPos;
    }
    public void deletePermsByRoleId(Integer id) {
        menuDao.deletePermsByRoleId(id);
    }

    public void addRight(Map<String, Integer> map) {
        menuDao.addRight(map);
    }

    public List<Integer> getPermsByRoleId(Integer roleId) {
        return menuDao.getPermsByRoleId(roleId);
    }
}
