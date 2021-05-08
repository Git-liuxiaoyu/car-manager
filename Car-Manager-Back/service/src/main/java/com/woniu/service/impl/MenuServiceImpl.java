package com.woniu.service.impl;

import com.woniu.adapter.MenuAdapter;
import com.woniu.po.MenuPo;
import com.woniu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
