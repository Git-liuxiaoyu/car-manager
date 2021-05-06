package com.woniu.service;


import com.woniu.domain.Employee;
import com.woniu.po.EmployeePo;
import com.woniu.po.MenuPo;

import java.util.List;

public interface EmployeeService {
    public EmployeePo getAccount(EmployeePo Employee);

    //查询用户名的权限
    List<MenuPo> mens(String name);

    List<Employee> findAll(String searchText,int pageIndex,int pageSize);

    int count(String searchText);

    void delById(Integer id);
}
