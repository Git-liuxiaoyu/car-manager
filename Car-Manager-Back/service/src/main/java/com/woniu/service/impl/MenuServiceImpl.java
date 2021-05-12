package com.woniu.service.impl;

import com.woniu.adapter.MenuAdapter;
import com.woniu.po.MenuPo;
import com.woniu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/05/08/16:37
 * @Description:
 */

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuAdapter menuAdapter;

    @Override
    public List<MenuPo> list() {
        return menuAdapter.list();
    }

    @Override
    public void deletePermsByRoleId(Integer id) {
        menuAdapter.deletePermsByRoleId(id);
    }

    @Override
    public void addRight(Map<String, Integer> map) {
        menuAdapter.addRight(map);
    }

    @Override
    public List<Integer> getPermsByRoleId(Integer roleId) {
        return menuAdapter.getPermsByRoleId(roleId);
    }
}
