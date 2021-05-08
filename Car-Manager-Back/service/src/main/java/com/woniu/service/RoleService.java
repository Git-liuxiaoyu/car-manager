package com.woniu.service;


import com.woniu.domain.Role;

import java.util.List;


public interface RoleService {
    public List<Role> roleList();

    List<Role> getById(Integer id);

    void deleteById(int id);

    void addRole(int id,int roleId);

    Integer count(String searchText);

    List<Role> findAll(String searchText, int pageIndex, int pageSize);

    void add(Role role);

    void delete(Integer id);

    List<Role> getByRoleId(Integer id);

    void update(Role role);
}
