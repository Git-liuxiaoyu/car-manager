package com.woniu.service.impl;

import com.woniu.adapter.RoleAdapter;
import com.woniu.domain.Role;
import com.woniu.po.RolePo;
import com.woniu.service.RoleService;
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
    public List<Role> roleList() {
        return roleAdapter.findRoleList();
    }

    @Override
    public List<Role> getById(Integer id) {
        return roleAdapter.getById(id);
    }

    @Override
    public void deleteById(int id) {
        roleAdapter.deleteById(id);
    }

    @Override
    public void addRole(int id,int roleId) {
        roleAdapter.addRole(id,roleId);
    }

    @Override
    public Integer count(String searchText) {
        return roleAdapter.count(searchText);
    }

    @Override
    public List<Role> findAll(String searchText, int pageIndex, int pageSize) {
        return roleAdapter.findAll(searchText,pageIndex,pageSize);
    }

    @Override
    public void add(Role role) {
        roleAdapter.add(role);
    }

    @Override
    public void delete(Integer id) {
        roleAdapter.delete(id);
    }

    @Override
    public List<Role> getByRoleId(Integer id) {
        return roleAdapter.getByRoleId(id);
    }

    @Override
    public void update(Role role) {
        roleAdapter.update(role);
    }
}
