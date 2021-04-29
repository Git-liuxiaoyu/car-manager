package com.woniu.service.impl;

import com.woniu.adapter.RoleAdapter;
import com.woniu.dao.RoleDao;
import com.woniu.po.Role;
import com.woniu.service.RoleService;
import org.apache.coyote.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {


    @Autowired
    private RoleAdapter roleAdapter;


    @Override
    public List<Role> roles() {

        return roleAdapter.findRoleLists();
    }
}
