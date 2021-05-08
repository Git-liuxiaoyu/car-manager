package com.woniu.service.impl;

import com.woniu.adapter.EmployeeAdapter;
import com.woniu.dao.EmployeeDao;
import com.woniu.domain.Employee;
import com.woniu.po.EmployeePo;
import com.woniu.po.MenuPo;
import com.woniu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/30/10:26
 * @Description:
 */

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeAdapter employeeAdapter;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public EmployeePo getAccount(EmployeePo employee) {
        return employeeDao.getAccount(employee);
    }

    @Override
    public List<MenuPo> mens(String name) {
        return employeeDao.mens(name);
    }

    //分页查询员工列表
    @Override
    public List<Employee> findAll(String searchText,int pageIndex,int pageSize) {

        return employeeAdapter.findEmployeeLists(searchText,pageIndex,pageSize);
    }

    @Override
    public int count(String searchText) {
        return employeeAdapter.count(searchText);
    }

    @Override
    public void delById(Integer id) {
        employeeAdapter.del(id);
    }

    @Override
    public void add(Employee employee) {
        employeeAdapter.add(employee);
    }

    @Override
    public void update(Employee updateEmployee) {
        employeeAdapter.update(updateEmployee);
    }

    @Override
    public Employee getByAccount(String account) {
       return employeeAdapter.getByAccount(account);
    }

    @Override
    public void updatePassWord(Employee employee) {
        employeeAdapter.updatePassWord(employee);
    }
}
